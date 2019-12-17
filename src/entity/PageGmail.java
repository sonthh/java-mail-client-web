package entity;

import java.util.List;

public class PageGmail {
	private List<Gmail> gmails;
	private Integer totalOfElements;
	private Integer pageNumber;
	private Integer totalOfPages;

	public List<Gmail> getGmails() {
		return gmails;
	}

	public void setGmails(List<Gmail> gmails) {
		this.gmails = gmails;
	}

	public Integer getTotalOfElements() {
		return totalOfElements;
	}

	public void setTotalOfElements(Integer totalOfElements) {
		this.totalOfElements = totalOfElements;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getTotalOfPages() {
		return totalOfPages;
	}

	public void setTotalOfPages(Integer totalOfPages) {
		this.totalOfPages = totalOfPages;
	}

}
