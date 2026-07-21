package com.itechro.cas.dao;

import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.common.PagedParamDTO;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BaseJDBCDao {

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(BaseJDBCDao.class);

    protected <T extends Serializable> Page<T> getPagedData(String dataQuery, Map<String, Object> paramsMap,
                                                            RowMapper<T> rowMapper, PagedParamDTO gridPramDTO) {
        try {
            Integer pageIndex = gridPramDTO.getPage() - 1;
            Integer rows = gridPramDTO.getRows();
            Integer start = pageIndex * rows;

            Long totalNoOfRecs = namedParameterJdbcTemplate.queryForObject(
                    this.getCountQuery(dataQuery), paramsMap, Long.class);

            paramsMap.put("startRowNo", start + 1);
            paramsMap.put("endRowNo", Math.min(start + rows, totalNoOfRecs));

            Collection<T> resultsList = namedParameterJdbcTemplate.query(
                    getPagedQuery(dataQuery), paramsMap, rowMapper);

            if (resultsList == null) {
                resultsList = new ArrayList<>();
            }

            return new Page<>(totalNoOfRecs, start, resultsList.size(), rows, resultsList);

        } catch (DataAccessException dae) {
            LOG.error("Database access error in getPagedData: {}", dae.getMessage(), dae);
            throw new RuntimeException("Error fetching paged data from database", dae);
        } catch (Exception e) {
            LOG.error("Unexpected error in getPagedData: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while fetching paged data", e);
        }
    }


    protected <T extends Serializable> Page<T> getPagedData(String dataQuery, MapSqlParameterSource paramsMap,
                                                            RowMapper<T> rowMapper, PagedParamDTO gridPramDTO) {
        try {
            Integer pageIndex = gridPramDTO.getPage() - 1;
            Integer rows = gridPramDTO.getRows();
            Integer start = pageIndex * rows;

            Long totalNoOfRecs = namedParameterJdbcTemplate.queryForObject(
                    this.getCountQuery(dataQuery), paramsMap, Long.class);

            paramsMap.addValue("start", start);
            paramsMap.addValue("noOfRows", rows);
            paramsMap.addValue("endRowNo", Math.min(start + rows, totalNoOfRecs));

            Collection<T> resultsList = namedParameterJdbcTemplate.query(
                    getPagedQuery(dataQuery), paramsMap, rowMapper);

            if (resultsList == null) {
                resultsList = new ArrayList<>();
            }

            return new Page<>(totalNoOfRecs, start, resultsList.size(), rows, resultsList);

        } catch (DataAccessException dae) {
            LOG.error("Database access error in getPagedData: {}", dae.getMessage(), dae);
            throw new RuntimeException("Database error occurred while fetching paged data", dae);
        } catch (Exception e) {
            LOG.error("Unexpected error in getPagedData: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error occurred while fetching paged data", e);
        }
    }


    // Get paged data for stored procedures
    protected <T extends Serializable> Page<T> getPagedData(List<T> resultArray, PagedParamDTO gridPramDTO) {
        try {
            int totalNoOfRecs = resultArray != null ? resultArray.size() : 0;

            if (totalNoOfRecs == 0) {
                LOG.warn("Empty result list passed to getPagedData.");
                return new Page<>(0, 0, 0, gridPramDTO.getRows(), new ArrayList<>());
            }

            Integer pageIndex = gridPramDTO.getPage() - 1;
            Integer rows = gridPramDTO.getRows();

            if (pageIndex < 0 || rows <= 0) {
                LOG.warn("Invalid pagination parameters. page: {}, rows: {}", gridPramDTO.getPage(), rows);
                throw new IllegalArgumentException("Invalid pagination parameters.");
            }

            int start = pageIndex * rows;
            int end = Math.min(start + rows, totalNoOfRecs);

            if (start >= totalNoOfRecs) {
                LOG.warn("Requested start index ({}) is beyond total records ({})", start, totalNoOfRecs);
                return new Page<>(totalNoOfRecs, start, 0, rows, new ArrayList<>());
            }

            Collection<T> resultsList = resultArray.subList(start, end);

            return new Page<>(totalNoOfRecs, start, resultsList.size(), rows, resultsList);

        } catch (IllegalArgumentException e) {
            LOG.error("Invalid pagination input in getPagedData: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error("Unexpected error in getPagedData: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while paginating result list", e);
        }
    }


    private String getPagedQuery(String query) {
        StringBuilder pagedQuery = new StringBuilder("SELECT * ");
        pagedQuery.append(" FROM ( SELECT pagedTable.*, ROWNUM rnum ");
        pagedQuery.append(" FROM ( ").append(query).append(" ) pagedTable ");
        pagedQuery.append(" WHERE ROWNUM <= :endRowNo ) ");
        pagedQuery.append(" WHERE rnum >= :startRowNo ");
        return pagedQuery.toString();
    }

    private String getCountQuery(String query) {
        StringBuilder countQuery = new StringBuilder();
        countQuery.append("SELECT count(*) FROM ( ");
        countQuery.append(query);
        countQuery.append(" ) count");

        return countQuery.toString();
    }
}
