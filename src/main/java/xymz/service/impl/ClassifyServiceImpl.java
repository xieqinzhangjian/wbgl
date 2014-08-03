package xymz.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import xymz.dao.ClassifyDao;
import xymz.model.ClassifySimple;
import xymz.pojo.Classify;
import xymz.pojo.Json;
import xymz.service.ClassifyService;
import xymz.utils.GetMenuUtil;

@Service("classifyService")
public class ClassifyServiceImpl implements ClassifyService {

	@Resource
	private ClassifyDao classifyDao;

	@Override
	public Json save(Classify classify) {
		Json j = new Json();
		Classify parent = null;
		if (classify.getParentId() != null) {
			parent = classifyDao.get(classify.getParentId());
		}

		Classify c = new Classify();
		if (parent != null) {
			c.setParent(parent);
		}
		c.setText(classify.getText());
		classifyDao.save(c);
		j.setSuccess(true);
		j.setMsg("修改成功");
		return j;

	}

	@Override
	public List<Classify> query() {
		return classifyDao.query();
	}

	@Override
	public Classify get(int id) {
		// TODO Auto-generated method stub
		return classifyDao.get(id);
	}

	@Override
	public Json delete(int id) {
		Json j = new Json();
		Classify c = classifyDao.get(id);
		c.setStatus(1);// 1标记删除
		classifyDao.update(c);
		j.setSuccess(true);
		return j;

	}

	@Override
	public Json update(Classify classify) {
		Json j = new Json();
		Classify c = classifyDao.get(classify.getId());
		c.setText(classify.getText());
		classifyDao.update(c);
		j.setSuccess(true);
		return j;

	}

	public List<Classify> queryAllparent() {
		List<Classify> 	classifyList = GetMenuUtil.AllParent();
		return classifyList;

	}

	/*
	 * @Override public List<Classify> queryAllchildren() { // TODO Auto-generated method stub return classifyDao.queryAllchildren(); }
	 */

	public void recursionClassify(Set<Classify> classifySet, List<ClassifySimple> classifSimpleList) {

		for (Classify classify : classifySet) {
			ClassifySimple cf = new ClassifySimple();
			cf.setId(classify.getId());
			cf.setText(classify.getText());
			classifSimpleList.add(cf);

			if (classify.getChildren().size() != 0) {
				recursionClassify(classify.getChildren(), classifSimpleList);
			}

		}
	}

	public static void fillterMenu(List<Classify> classifyList) {
		Iterator<Classify> menuIterator = classifyList.iterator();
		while (menuIterator.hasNext()) {
			Classify classify = menuIterator.next();
			if (classify.getStatus() == 1) {
				menuIterator.remove();

			}
			Set<Classify> childrenMenuSet = classify.getChildren();
			if (childrenMenuSet.size() != 0) {
				Iterator<Classify> childrenMenuIterator = childrenMenuSet.iterator();
				while (childrenMenuIterator.hasNext()) {
					Classify childrenMenu = childrenMenuIterator.next();
					if (classify.getStatus() == 1) {
						childrenMenuIterator.remove(); // 这个操作会导致
					}

				}
			}
		}

	}

}
