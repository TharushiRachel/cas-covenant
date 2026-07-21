package com.itechro.cas.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author sankaw
 */
public class WorkGroupUtil {

    private static final int INQUIRY = 5;
    private static final int ENTERER_BRANCH_DEPARTMENT = 10;
    private static final int AGENT = 15;
    private static final int SUPERVISOR_BRANCH_DEPARTMENT = 20;
    private static final int MANAGER_BRANCH_DEPARTMENT = 50;
    private static final int BRANCH_DEPARTMENT_COMITIES = 51;
    private static final int DIVISION_SCRUTINIZER = 52;
    private static final int DIVISION_SUPERVISOR = 53;
    private static final int SPECIAL_UNITS = 54;
    private static final int REGIONAL_MANAGER = 71;
    private static final int SENIOR_MANAGER = 60;
    private static final int CHIEF_MANAGER = 72;
    private static final int APPROVING_COMITIES = 67;
    private static final int AGM = 73;
    private static final int DGM = 74;
    private static final int SDGM = 78;
    private static final int ASSISTANT = 79;
    private static final int MD = 80;
    private static final int DIRECTOR_BOARD = 95;
    private static final int ADMIN = 98;
    private static final int SUPER_USER = 99;
    private static final int INVALID_CODE = -1;

    //private static final int ALL = 110;
    //private static final int ALL_BR = 115;
    //private static final int ALL_DEP = 120;
    //private static final int DIF = 125;
    public static String getWorkGroupName(int workGroupCode) {
        switch (workGroupCode) {
            case 5:
                return "INQUIRY";
            case 10:
                return "ENTERER";
            case 15:
                return "AGENT";
            case 20:
                return "SUPERVISOR";
            case 50:
                return "MANAGER";
            case 51:
                return "BRANCH_DEPARTMENT_COMITIES";
            case 52:
                return "DIVISION_SCRUTINIZER";
            case 53:
                return "DIVISION_SUPERVISOR";
            case 54:
                return "SPECIAL_UNITS";
            case 71:
                return "REGIONAL_MANAGER";
            case 60:
                return "SENIOR_MANAGER";
            case 72:
                return "CHIEF_MANAGER";
            case 67:
                return "APPROVING_COMITIES";
            case 73:
                return "AGM";
            case 75:
                return "DGM";
            case 78:
                return "SDGM";
            case 79:
                return "ASSISTANT";
            case 80:
                return "MD";
            case 95:
                return "DIRECTOR_BOARD";
            case 98:
                return "ADMIN";
            case 99:
                return "SUPER_USER";
            default:
                return "INVALID_CODE";
        }
    }

    public static int getNextGroupLevel(int currentGroupLevel) {
        switch (currentGroupLevel) {
            //case  5: return "INQUIRY";
            case 10:
                return 20;
            case 20:
                return 50;
            case 50:
                return 52;
            case 52:
                return 53;
            case 53:
                return 55;
            case 55:
                return 60;
            case 60:
                return 65;
            case 65:
                return 67;
            case 67:
                return 70;
            case 70:
                return 75;
            case 71:
                return 72;
            case 72:
                return 73;
            case 73:
            case 75:
                return 80;
            case 80:
                return 90;
            case 90:
                return 95;
            case 95:
                return 98;
            case 98:
                return -98;
            case 99:
                return -99;
            default:
                return -1;
        }
    }

    /**
     * Sanka
     *
     * @param currentGroupLevel user security class
     * @return
     */
    public static int[] getNextForwardGroupLevels(int currentGroupLevel) {
        switch (currentGroupLevel) {
            //case  5: return "INQUIRY";
            case ASSISTANT:
                return new int[]{SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT, DIVISION_SCRUTINIZER, SPECIAL_UNITS, REGIONAL_MANAGER, SENIOR_MANAGER, CHIEF_MANAGER, APPROVING_COMITIES, DGM, SDGM, MD, AGM, DIRECTOR_BOARD};
            case AGENT:
                return new int[]{ASSISTANT, DIVISION_SCRUTINIZER, MANAGER_BRANCH_DEPARTMENT};
            case ENTERER_BRANCH_DEPARTMENT:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER, CHIEF_MANAGER};
            case SUPERVISOR_BRANCH_DEPARTMENT:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT};
            case MANAGER_BRANCH_DEPARTMENT:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT, DIVISION_SCRUTINIZER, SPECIAL_UNITS, REGIONAL_MANAGER, SENIOR_MANAGER, CHIEF_MANAGER, APPROVING_COMITIES, DIVISION_SUPERVISOR}; //MANAGER_BRANCH_DEPARTMENT
            case DIVISION_SCRUTINIZER:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, DIVISION_SUPERVISOR, DIVISION_SCRUTINIZER, MANAGER_BRANCH_DEPARTMENT};
            case DIVISION_SUPERVISOR:
                return new int[]{MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER, SENIOR_MANAGER, CHIEF_MANAGER, APPROVING_COMITIES};
            case SPECIAL_UNITS:
                return new int[]{MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER, SENIOR_MANAGER, CHIEF_MANAGER, APPROVING_COMITIES};
            case REGIONAL_MANAGER:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER, CHIEF_MANAGER, AGM, DGM, MD};
            case SENIOR_MANAGER:
                return new int[]{ASSISTANT, CHIEF_MANAGER, APPROVING_COMITIES, AGM, DGM, SDGM};
            case CHIEF_MANAGER:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER, CHIEF_MANAGER, AGM, DGM, MD};
            case APPROVING_COMITIES:
                return new int[]{ASSISTANT, AGM, DGM, SDGM};
            case AGM:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER, CHIEF_MANAGER, AGM, DGM, MD};
            case DGM:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER, CHIEF_MANAGER, AGM, DGM, MD};
            case SDGM:
                return new int[]{ASSISTANT, MD, DIRECTOR_BOARD, SDGM, AGM, DGM};
            case MD:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER, CHIEF_MANAGER, AGM, DGM, MD};
            //case 95: return new int[] {20,50};
            case 98:
                return new int[]{-1};
            case 99:
                return new int[]{-1};
            default:
                return new int[]{-1};
        }
    }

    /**
     * Sanka
     *
     * @param currentGroupLevel user security class
     * @return
     */
    public static String[] getNextForwardGroupLevelsWithNames(int currentGroupLevel) {

        try {
            int[] nextLevels = getNextForwardGroupLevels(currentGroupLevel);

            String[] lvlNames = new String[nextLevels.length];
            for (int i = 0; i < nextLevels.length; i++) {
                lvlNames[i] = getWorkGroupName(nextLevels[i]) + "|" + nextLevels[i];
            }
            return lvlNames;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * Sanka
     *
     * @param currentGroupLevel list of user security classes
     * @return
     */
    public static int[] getNextForwardGroupLevels(int[] currentGroupLevel) {
        ArrayList<Integer> groups = new ArrayList<>();

        for (int i = 0; i < currentGroupLevel.length; i++) {
            switch (currentGroupLevel[i]) {
                //case  5: return "INQUIRY";
                case ASSISTANT:
                    groups.add(SUPERVISOR_BRANCH_DEPARTMENT);
                    groups.add(MANAGER_BRANCH_DEPARTMENT);
                    groups.add(AGM);
                    groups.add(DIRECTOR_BOARD);
                case ENTERER_BRANCH_DEPARTMENT:
                    groups.add(SUPERVISOR_BRANCH_DEPARTMENT);
                    groups.add(MANAGER_BRANCH_DEPARTMENT);
                case SUPERVISOR_BRANCH_DEPARTMENT:
                    groups.add(ENTERER_BRANCH_DEPARTMENT);
                    groups.add(MANAGER_BRANCH_DEPARTMENT);
                case MANAGER_BRANCH_DEPARTMENT:
                    groups.add(DIVISION_SCRUTINIZER);
                    groups.add(SPECIAL_UNITS);
                    groups.add(REGIONAL_MANAGER);
                    groups.add(SENIOR_MANAGER);
                    groups.add(CHIEF_MANAGER);
                    groups.add(APPROVING_COMITIES); //MANAGER_BRANCH_DEPARTMENT
                case DIVISION_SCRUTINIZER:
                    groups.add(DIVISION_SUPERVISOR);
                case DIVISION_SUPERVISOR:
                    groups.add(MANAGER_BRANCH_DEPARTMENT);
                    groups.add(REGIONAL_MANAGER);
                    groups.add(SENIOR_MANAGER);
                    groups.add(CHIEF_MANAGER);
                    groups.add(APPROVING_COMITIES);
                case SPECIAL_UNITS:
                    groups.add(MANAGER_BRANCH_DEPARTMENT);
                    groups.add(REGIONAL_MANAGER);
                    groups.add(SENIOR_MANAGER);
                    groups.add(CHIEF_MANAGER);
                    groups.add(APPROVING_COMITIES);
                case REGIONAL_MANAGER:
                    groups.add(DIVISION_SUPERVISOR);
                    groups.add(DIVISION_SCRUTINIZER);
                    groups.add(SENIOR_MANAGER);
                    groups.add(CHIEF_MANAGER);
                    groups.add(APPROVING_COMITIES);
                    groups.add(AGM);
                    groups.add(DGM);
                    groups.add(SDGM);
                case SENIOR_MANAGER:
                    groups.add(CHIEF_MANAGER);
                    groups.add(APPROVING_COMITIES);
                    groups.add(AGM);
                    groups.add(DGM);
                    groups.add(SDGM);
                case CHIEF_MANAGER:
                    groups.add(APPROVING_COMITIES);
                    groups.add(AGM);
                    groups.add(DGM);
                    groups.add(SDGM);
                case APPROVING_COMITIES:
                    groups.add(AGM);
                    groups.add(DGM);
                    groups.add(SDGM);
                case AGM:
                    groups.add(DGM);
                    groups.add(SDGM);
                    groups.add(MD);
                    groups.add(DIRECTOR_BOARD);
                case DGM:
                    groups.add(SDGM);
                    groups.add(MD);
                    groups.add(DIRECTOR_BOARD);
                case SDGM:
                    groups.add(MD);
                    groups.add(DIRECTOR_BOARD);
                case MD:
                    groups.add(DIRECTOR_BOARD);
                    //case 95: return new int[] {20,50};
                case 98:
                    return new int[]{-1};
                case 99:
                    return new int[]{-1};
                default:
                    return new int[]{-1};
            }
        }
        // return null;
        return Arrays.stream(groups.toArray()).mapToInt(o -> (int) o).toArray();
    }

    /**
     * Sanka
     *
     * @param currentGroupLevel user security class
     * @return
     */
    public static String[] getReturnGroupLevelsWithNames(int currentGroupLevel) {
        try {
            int[] rtnLevels = getReturnGroupLevels(currentGroupLevel);
            //LOGGER.info("getReturnGroupLevelsWithNames nextLevels : "+Arrays.toString(rtnLevels));
            String[] lvlNames = new String[rtnLevels.length];
            for (int i = 0; i < rtnLevels.length; i++) {
                lvlNames[i] = getWorkGroupName(rtnLevels[i]) + "|" + rtnLevels[i];
            }
            //LOGGER.info("getReturnGroupLevelsWithNames lvlNames : "+Arrays.toString(lvlNames));
            return lvlNames;
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * Sanka
     *
     * @param currentGroupLevel user security class
     * @return
     */
    public static String[] getReturnGroupLevelsWithNamesForBranch(int currentGroupLevel) {
        try {
            int[] rtnLevels = getReturnGroupLevels(currentGroupLevel);
            String[] lvlNames = new String[rtnLevels.length];
            for (int i = 0; i < rtnLevels.length; i++) {
                if (rtnLevels[i] <= MANAGER_BRANCH_DEPARTMENT) {
                    lvlNames[i] = getWorkGroupName(rtnLevels[i]) + "|" + rtnLevels[i];
                }
            }
            return lvlNames;
        } catch (Exception ex) {
            return null;
        }

    }

    public static int[] getReturnGroupLevels(int currentGroupLevel) {
        switch (currentGroupLevel) {
            //case  5: return "INQUIRY";
            case ASSISTANT:
                return new int[]{ENTERER_BRANCH_DEPARTMENT};
            case AGENT:
                return new int[]{ASSISTANT, DIVISION_SCRUTINIZER, MANAGER_BRANCH_DEPARTMENT};
            case ENTERER_BRANCH_DEPARTMENT:
                return new int[]{SUPERVISOR_BRANCH_DEPARTMENT, ENTERER_BRANCH_DEPARTMENT};
            case SUPERVISOR_BRANCH_DEPARTMENT:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT};
            case MANAGER_BRANCH_DEPARTMENT:
                return new int[]{ENTERER_BRANCH_DEPARTMENT, SUPERVISOR_BRANCH_DEPARTMENT, MANAGER_BRANCH_DEPARTMENT, DIVISION_SCRUTINIZER, DIVISION_SUPERVISOR};
            case BRANCH_DEPARTMENT_COMITIES:
                return new int[]{MANAGER_BRANCH_DEPARTMENT};
            case DIVISION_SCRUTINIZER:
                return new int[]{MANAGER_BRANCH_DEPARTMENT, DIVISION_SCRUTINIZER, SUPERVISOR_BRANCH_DEPARTMENT,};
            case DIVISION_SUPERVISOR:
                return new int[]{MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER, DIVISION_SCRUTINIZER, DIVISION_SUPERVISOR};
            case SPECIAL_UNITS:
                return new int[]{MANAGER_BRANCH_DEPARTMENT, REGIONAL_MANAGER};
            case REGIONAL_MANAGER:
                return new int[]{MANAGER_BRANCH_DEPARTMENT, SPECIAL_UNITS, DIVISION_SUPERVISOR, REGIONAL_MANAGER};
            case SENIOR_MANAGER:
                return new int[]{MANAGER_BRANCH_DEPARTMENT, SPECIAL_UNITS, DIVISION_SUPERVISOR, REGIONAL_MANAGER, SENIOR_MANAGER};
            case CHIEF_MANAGER:
                return new int[]{MANAGER_BRANCH_DEPARTMENT, SPECIAL_UNITS, DIVISION_SUPERVISOR, REGIONAL_MANAGER, CHIEF_MANAGER, SENIOR_MANAGER};
            case APPROVING_COMITIES:
                return new int[]{MANAGER_BRANCH_DEPARTMENT, SPECIAL_UNITS, DIVISION_SUPERVISOR, REGIONAL_MANAGER, CHIEF_MANAGER, SENIOR_MANAGER, APPROVING_COMITIES};
            case AGM:
                return new int[]{ASSISTANT, REGIONAL_MANAGER, SENIOR_MANAGER, CHIEF_MANAGER, APPROVING_COMITIES, AGM};
            case DGM:
                return new int[]{REGIONAL_MANAGER, SENIOR_MANAGER, CHIEF_MANAGER, APPROVING_COMITIES, AGM, DGM};
            case SDGM:
                return new int[]{REGIONAL_MANAGER, SENIOR_MANAGER, CHIEF_MANAGER, APPROVING_COMITIES, AGM, DGM, SDGM};
            case MD:
                return new int[]{AGM, DGM, SDGM, DIRECTOR_BOARD};
            case DIRECTOR_BOARD:
                return new int[]{ASSISTANT, AGM, DGM, SDGM, MD};
            //case 95: return new int[] {20,50};
            case 98:
                return new int[]{-1};
            case 99:
                return new int[]{-1};
            default:
                return new int[]{-1};
        }
    }

    /**
     * @return the INQUIRY
     */
    public static int getSERETORY() {
        return ASSISTANT;
    }

    public static int getAssistantUPMGroup() {
        return ASSISTANT;
    }

    /**
     * @return the INQUIRY
     */
    public static int getINQUIRY() {
        return INQUIRY;
    }

    /**
     * @return the INQUIRY
     */
    public static int getAGENT() {
        return AGENT;
    }

    /**
     * @return the ENTERER_BRANCH_DEPARTMENT
     */
    public static int getENTERER_BRANCH_DEPARTMENT() {
        return ENTERER_BRANCH_DEPARTMENT;
    }

    /**
     * @return the SUPERVISOR_BRANCH_DEPARTMENT
     */
    public static int getSUPERVISOR_BRANCH_DEPARTMENT() {
        return SUPERVISOR_BRANCH_DEPARTMENT;
    }

    /**
     * @return the MANAGER_BRANCH_DEPARTMENT
     */
    public static int getMANAGER_BRANCH_DEPARTMENT() {
        return MANAGER_BRANCH_DEPARTMENT;
    }

    /**
     * @return the BRANCH_DEPARTMENT_COMITIES
     */
    public static int getBRANCH_DEPARTMENT_COMITIES() {
        return BRANCH_DEPARTMENT_COMITIES;
    }

    /**
     * @return the SPECIAL_UNITS
     */
    public static int getSPECIAL_UNITS() {
        return SPECIAL_UNITS;
    }

    /**
     * @return the DIVISION_SCRUTINIZER
     */
    public static int getDIVISION_SCRUTINIZER() {
        return DIVISION_SCRUTINIZER;
    }

    /**
     * @return the DIVISION_SUPERVISOR
     */
    public static int getDIVISION_SUPERVISOR() {
        return DIVISION_SUPERVISOR;
    }

    /**
     * @return the REGIONAL_MANAGER
     */
    public static int getREGIONAL_MANAGER() {
        return REGIONAL_MANAGER;
    }

    /**
     * @return the SENIOR_MANAGER
     */
    public static int getSENIOR_MANAGER() {
        return SENIOR_MANAGER;
    }

    /**
     * @return the CHIEF_MANAGER
     */
    public static int getCHIEF_MANAGER() {
        return CHIEF_MANAGER;
    }

    /**
     * @return the APPROVING_COMITIES
     */
    public static int getAPPROVING_COMITIES() {
        return APPROVING_COMITIES;
    }

    /**
     * @return the AGM
     */
    public static int getAGM() {
        return AGM;
    }

    /**
     * @return the DGM
     */
    public static int getDGM() {
        return DGM;
    }

    /**
     * @return the SDGM
     */
    public static int getSDGM() {
        return SDGM;
    }

    /**
     * @return the MD
     */
    public static int getMD() {
        return MD;
    }

    /**
     * @return the DIRECTOR_BOARD
     */
    public static int getDIRECTOR_BOARD() {
        return DIRECTOR_BOARD;
    }

    /**
     * @return the ADMIN
     */
    public static int getADMIN() {
        return ADMIN;
    }

    /**
     * @return the SUPER_USER
     */
    public static int getSUPER_USER() {
        return SUPER_USER;
    }

    /**
     * @return the INVALID_CODE
     */
    public static int getINVALID_CODE() {
        return INVALID_CODE;
    }

}