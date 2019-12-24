
/**
 * 根据传入的 List 及 Page 相关参数，进行分页展示
 * @param  list [description]
 * @param  page [description]
 * @return      [description]
 */
private Page<Map<String, String>> generatePage(List<Map<String, String>> list, Page<Map<String, String>> page) {
	int pageNo = page.getPageNo();
	int pageSize = page.getPageSize();
	int startIndex = (pageNo - 1) * pageSize;
	int endIndex = (pageNo * pageSize - 1);
	int count = list.size();
	if (endIndex > count) {
		endIndex = count;
	}
	list = list.subList(startIndex, endIndex);
	page.setTotalCount(count);
	page.setResult(list);
	return page;
}