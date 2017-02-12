package web;

import java.util.List;

public class Page<T> {
	//book的属性封装
	private List<T> list;
	//当前页码
	private int pageNo;
	//每页记录数
	private int pageSize = 3;
	//总记录数
	private long totalItemNum;
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	//需要校验
	/**校验传入的页码是否合法
	 * @return
	 */
	public int getPageNo() {
		if (pageNo < 0) {
			pageNo = 1 ;
		}
		if (pageNo > getTotalPageNum()) {
			pageNo = getTotalPageNum();
		}
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalItemNum(long totalItemNum) {
		this.totalItemNum = totalItemNum;
	}

	
	public Page(int pageNo) {
		this.pageNo = pageNo;
	}
	
	/**获取总页数
	 * @return
	 */
	public int getTotalPageNum() {
		int totalPageNum = (int) (totalItemNum / pageSize);
		if (totalItemNum % pageSize != 0 ) {
			totalPageNum++;
		}
		
		return totalPageNum;
	}
	
	/**判断是否有下一页
	 * @return
	 */
	public boolean isHasNext() {
		if (getPageNo() < getTotalPageNum() ) {
			return true;
		}
		return false;
	}
	
	/**判断是否有前一页
	 * @return
	 */
	public boolean isHasPrev() {
		if (getPageNo() >1 ) {
			return true;
		}
		return false;
	}
	
	/**下一页
	 * @return 当前页
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return getPageNo() + 1;
		}
		return getPageNo();
	}
	
	/**上一页
	 * @return 当前页
	 */
	public int getPrevPage() {
		if (isHasPrev()) {
			return getPageNo() -1 ;
		}
		return getPageNo();
	}
	
	
}
