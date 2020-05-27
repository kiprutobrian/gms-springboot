package cloudit.africa.GMS.Model;

public class DriveYearly {
	
	private String year;
	private Integer totalNoOfFiles;
	private Long totalSize;
	
	
	
	
	public DriveYearly(String year, Integer totalNoOfFiles, Long totalSize) {
		super();
		this.year = year;
		this.totalNoOfFiles = totalNoOfFiles;
		this.totalSize = totalSize;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Integer getTotalNoOfFiles() {
		return totalNoOfFiles;
	}
	public void setTotalNoOfFiles(Integer totalNoOfFiles) {
		this.totalNoOfFiles = totalNoOfFiles;
	}
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public String toString() {
		return "DriveYearly [year=" + year + ", totalNoOfFiles=" + totalNoOfFiles + ", totalSize=" + totalSize + "]";
	}

	
}
