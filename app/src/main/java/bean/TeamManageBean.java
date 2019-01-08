package bean;


import java.util.List;

public class TeamManageBean {
    private boolean success;
    private String errormessage;
    private List<CellsBean> cells;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public List<CellsBean> getCells() {
        return cells;
    }

    public void setCells(List<CellsBean> cells) {
        this.cells = cells;
    }

    public static class CellsBean {

        private String ranksid;
        private String ranksname;
        private String rankstype;
        private String areas;
        private String ownman;
        private String owntel;
        private String linkman;
        private String linktel;
        private String pepsum;
        private String competentdetp;
        private String specialtydesc;
        private String xcoordinate;
        private String ycoordinate;
        private String address;
        private String deptid;
        private String parentdeptid;
        private String memo;
        private String equipmentstatusname;

        public String getRanksid() {
            return ranksid;
        }

        public void setRanksid(String ranksid) {
            this.ranksid = ranksid;
        }

        public String getRanksname() {
            return ranksname;
        }

        public void setRanksname(String ranksname) {
            this.ranksname = ranksname;
        }

        public String getRankstype() {
            return rankstype;
        }

        public void setRankstype(String rankstype) {
            this.rankstype = rankstype;
        }

        public String getAreas() {
            return areas;
        }

        public void setAreas(String areas) {
            this.areas = areas;
        }

        public String getOwnman() {
            return ownman;
        }

        public void setOwnman(String ownman) {
            this.ownman = ownman;
        }

        public String getOwntel() {
            return owntel;
        }

        public void setOwntel(String owntel) {
            this.owntel = owntel;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getLinktel() {
            return linktel;
        }

        public void setLinktel(String linktel) {
            this.linktel = linktel;
        }

        public String getPepsum() {
            return pepsum;
        }

        public void setPepsum(String pepsum) {
            this.pepsum = pepsum;
        }

        public String getCompetentdetp() {
            return competentdetp;
        }

        public void setCompetentdetp(String competentdetp) {
            this.competentdetp = competentdetp;
        }

        public String getSpecialtydesc() {
            return specialtydesc;
        }

        public void setSpecialtydesc(String specialtydesc) {
            this.specialtydesc = specialtydesc;
        }

        public String getXcoordinate() {
            return xcoordinate;
        }

        public void setXcoordinate(String xcoordinate) {
            this.xcoordinate = xcoordinate;
        }

        public String getYcoordinate() {
            return ycoordinate;
        }

        public void setYcoordinate(String ycoordinate) {
            this.ycoordinate = ycoordinate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDeptid() {
            return deptid;
        }

        public void setDeptid(String deptid) {
            this.deptid = deptid;
        }

        public String getParentdeptid() {
            return parentdeptid;
        }

        public void setParentdeptid(String parentdeptid) {
            this.parentdeptid = parentdeptid;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getEquipmentstatusname() {
            return equipmentstatusname;
        }

        public void setEquipmentstatusname(String equipmentstatusname) {
            this.equipmentstatusname = equipmentstatusname;
        }
    }
}
