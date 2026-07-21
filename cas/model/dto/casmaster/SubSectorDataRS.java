package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.model.dto.facility.SubSectorDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubSectorDataRS implements Serializable {

    private Map<Integer, List<SubSectorDTO>> sectorWiseSubSectorMap;

    private List<SubSectorDTO> subSectorDTOList;

    public Map<Integer, List<SubSectorDTO>> getSectorWiseSubSectorMap() {
        if (sectorWiseSubSectorMap == null) {
            sectorWiseSubSectorMap = new HashMap<>();
        }
        return sectorWiseSubSectorMap;
    }

    public void setSectorWiseSubSectorMap(Map<Integer, List<SubSectorDTO>> sectorWiseSubSectorMap) {
        this.sectorWiseSubSectorMap = sectorWiseSubSectorMap;
    }

    public List<SubSectorDTO> getSubSectorDTOList() {
        if (subSectorDTOList == null) {
            subSectorDTOList = new ArrayList<>();
        }
        return subSectorDTOList;
    }

    public void setSubSectorDTOList(List<SubSectorDTO> subSectorDTOList) {
        this.subSectorDTOList = subSectorDTOList;
    }
}
