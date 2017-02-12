package web;

import java.util.List;

public class Page<T> {
	//book�����Է�װ
	private List<T> list;
	//��ǰҳ��
	private int pageNo;
	//ÿҳ��¼��
	private int pageSize = 3;
	//�ܼ�¼��
	private long totalItemNum;
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	//��ҪУ��
	/**У�鴫���ҳ���Ƿ�Ϸ�
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
	
	/**��ȡ��ҳ��
	 * @return
	 */
	public int getTotalPageNum() {
		int totalPageNum = (int) (totalItemNum / pageSize);
		if (totalItemNum % pageSize != 0 ) {
			totalPageNum++;
		}
		
		return totalPageNum;
	}
	
	/**�ж��Ƿ�����һҳ
	 * @return
	 */
	public boolean isHasNext() {
		if (getPageNo() < getTotalPageNum() ) {
			return true;
		}
		return false;
	}
	
	/**�ж��Ƿ���ǰһҳ
	 * @return
	 */
	public boolean isHasPrev() {
		if (getPageNo() >1 ) {
			return true;
		}
		return false;
	}
	
	/**��һҳ
	 * @return ��ǰҳ
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return getPageNo() + 1;
		}
		return getPageNo();
	}
	
	/**��һҳ
	 * @return ��ǰҳ
	 */
	public int getPrevPage() {
		if (isHasPrev()) {
			return getPageNo() -1 ;
		}
		return getPageNo();
	}
	
	
}
