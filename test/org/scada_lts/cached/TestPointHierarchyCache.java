package org.scada_lts.cached;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.scada_lts.cache.PointHierarchyCache;
import org.scada_lts.dao.model.pointhierarchy.PointHierarchyDataSource;
import org.scada_lts.dao.model.pointhierarchy.PointHierarchyNode;

public class TestPointHierarchyCache {

	private PointHierarchyCache cache;
	private final PointHierarchyDataSource POINT_HIERARCHY_DS=null;
	
	private final int keyFolder1=1;
	private final int keyFolder2=2;
	private final int keyFolder3=3;
	private final int keyFolder4=4;
	private final int keyFolder5=5;
	private final int keyPoint1=1;
	private final int keyPoint2=2;
	private final int keyPoint3=3;
	private final int keyPoint4=4;
	private final int keyPoint5=5;
	private final int keyPoint6=6;
	private final int keyPoint7=7;
		
	private PointHierarchyNode phn1 = new PointHierarchyNode(keyFolder1, PointHierarchyCache.ROOT, "test1", PointHierarchyCache.IS_FOLDER, POINT_HIERARCHY_DS);
	private PointHierarchyNode phn2 = new PointHierarchyNode(keyFolder2, keyFolder1, "test2", PointHierarchyCache.IS_FOLDER, POINT_HIERARCHY_DS);
	private PointHierarchyNode phn3 = new PointHierarchyNode(keyFolder3, keyFolder2, "test3", PointHierarchyCache.IS_FOLDER, POINT_HIERARCHY_DS);
	private PointHierarchyNode phn4 = new PointHierarchyNode(keyFolder4, PointHierarchyCache.ROOT, "test4", PointHierarchyCache.IS_FOLDER,  POINT_HIERARCHY_DS);
	private PointHierarchyNode phn5 = new PointHierarchyNode(keyFolder5, PointHierarchyCache.ROOT, "test5", PointHierarchyCache.IS_FOLDER,  POINT_HIERARCHY_DS);
	
	private PointHierarchyNode phnP1 = new PointHierarchyNode(keyPoint1, PointHierarchyCache.ROOT, "point1", PointHierarchyCache.IS_NOT_FOLDER,  POINT_HIERARCHY_DS);
	private PointHierarchyNode phnP2 = new PointHierarchyNode(keyPoint2, PointHierarchyCache.ROOT, "point2", PointHierarchyCache.IS_NOT_FOLDER,  POINT_HIERARCHY_DS);
	private PointHierarchyNode phnP3 = new PointHierarchyNode(keyPoint3, PointHierarchyCache.ROOT, "point3", PointHierarchyCache.IS_NOT_FOLDER,  POINT_HIERARCHY_DS);
	private PointHierarchyNode phnP4 = new PointHierarchyNode(keyPoint4, keyFolder4, "point4", PointHierarchyCache.IS_NOT_FOLDER,  POINT_HIERARCHY_DS);
	private PointHierarchyNode phnP5 = new PointHierarchyNode(keyPoint5, keyFolder5, "point5", PointHierarchyCache.IS_NOT_FOLDER,  POINT_HIERARCHY_DS);
	private PointHierarchyNode phnP6 = new PointHierarchyNode(keyPoint6, keyFolder3, "point6", PointHierarchyCache.IS_NOT_FOLDER,  POINT_HIERARCHY_DS);
	private PointHierarchyNode phnP7 = new PointHierarchyNode(keyPoint7, keyFolder3, "point7", PointHierarchyCache.IS_NOT_FOLDER,  POINT_HIERARCHY_DS);
	
	@Before
	public void init() throws Exception {
		cache = new PointHierarchyCache(false);
	}
	
	
	private boolean containInCache(List<PointHierarchyNode> lst, int key, boolean isFolder){
		for (PointHierarchyNode p: lst) {
			if (p.getKey()==key && (p.isFolder()==isFolder)) {
				return true;
			}
		}
		return false;
	};
	
	private PointHierarchyNode findFolder(List<PointHierarchyNode> lst, int key){
		for (PointHierarchyNode p: lst) {
			if (p.getKey()==key && (p.isFolder()==true)) {
				return p;
			}
		}
		return null;
	};
	
	@Test
	public void addPointAndFolderAndPointMoveToFolder() throws Exception {
		cache.addPoint(phnP1);
		boolean contain = containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT),keyPoint1, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain );
		
		cache.addFolder(phn1);
		boolean contain1 = containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT),keyFolder1,PointHierarchyCache.IS_FOLDER);
		assertEquals(true,  contain1 );
		
		cache.move(PointHierarchyCache.ROOT, keyFolder1, keyPoint1, PointHierarchyCache.IS_NOT_FOLDER);
		boolean contain2 = containInCache(cache.getOnBaseParentId(keyFolder1),keyPoint1, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain2 );
		cache.printTreeInCash("", 0);

		
	}

	@Test
	public void addFolders() throws Exception {
		cache.addFolder(phn1);
		boolean contain = containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT),keyFolder1,PointHierarchyCache.IS_FOLDER);
		assertEquals(true,  contain );
		
		cache.addFolder(phn2);
		boolean contain1 = containInCache(cache.getOnBaseParentId(keyFolder1),keyFolder2,PointHierarchyCache.IS_FOLDER);
		assertEquals(true,  contain1 );
		
		cache.addFolder(phn3);
		boolean contain2 = containInCache(cache.getOnBaseParentId(keyFolder2),keyFolder3,PointHierarchyCache.IS_FOLDER);
		assertEquals(true,  contain2 );
		
		cache.addFolder(phn4);
		boolean contain3 = containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT),keyFolder4,PointHierarchyCache.IS_FOLDER);
		assertEquals(true,  contain3 );
		
		cache.addFolder(phn5);
		boolean contain4 = containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT),keyFolder5,PointHierarchyCache.IS_FOLDER);
		assertEquals(true,  contain4 );
		
	}
		
	
	@Test
	public void addPoints() {

		cache.addPoint(phnP1);
		boolean contain = containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT),keyPoint1, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain );
		
		cache.addPoint(phnP2);
		boolean contain1 = containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT),keyPoint2, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain1 );
		
		cache.addPoint(phnP3);
		boolean contain2 = containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT),keyPoint3, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain2 );
		
	}
	
	@Test
	public void addPointsInFolder() throws Exception {
		addFolders();
		
		cache.addPoint(phnP4);
		boolean contain3 = containInCache(cache.getOnBaseParentId(keyFolder4),keyPoint4, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain3 );
		
		cache.addPoint(phnP5);
		//printTreeInCash("", 0);
		boolean contain4 = containInCache(cache.getOnBaseParentId(keyFolder5),keyPoint5, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain4 );
		
		cache.addPoint(phnP6);
		boolean contain5 = containInCache(cache.getOnBaseParentId(keyFolder3),keyPoint6, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain5 );

		cache.addPoint(phnP7);
		boolean contain6 = containInCache(cache.getOnBaseParentId(keyFolder3),keyPoint7, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain6 );

	}

	@Test
	public void movePoint1InToFolder1() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		
		cache.move(PointHierarchyCache.ROOT, keyFolder1, keyPoint1, PointHierarchyCache.IS_NOT_FOLDER);
		boolean contain = containInCache(cache.getOnBaseParentId(keyFolder1),keyPoint1, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain );
		cache.printTreeInCash("", 0);
	}
	
	@Test
	public void movePoint2InToFolder2() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		
		cache.move(PointHierarchyCache.ROOT, keyFolder2, keyPoint2, PointHierarchyCache.IS_NOT_FOLDER);
		boolean contain = containInCache(cache.getOnBaseParentId(keyFolder2),keyPoint2, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain );
		cache.printTreeInCash("", 0);
	}
	
	@Test
	public void movePointFromFolder2ToFolder3() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		
		cache.move(PointHierarchyCache.ROOT, keyFolder2, keyPoint2, PointHierarchyCache.IS_NOT_FOLDER);
		boolean contain = containInCache(cache.getOnBaseParentId(keyFolder2),keyPoint2, PointHierarchyCache.IS_NOT_FOLDER);
		assertEquals(true,  contain );
		cache.printTreeInCash("", 0);
	}
	
	@Test
	public void movePoint3InToFolder3() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		cache.printTreeInCash("", 0);
		cache.move(PointHierarchyCache.ROOT, keyFolder3, keyPoint3, PointHierarchyCache.IS_NOT_FOLDER);
		boolean contain = containInCache(cache.getOnBaseParentId(keyFolder3),keyPoint3, PointHierarchyCache.IS_NOT_FOLDER);
		
		assertEquals(true,  contain );
		
	}
	
	@Test
	public void movePoint4InToFolder3() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		cache.printTreeInCash("", 0);
		cache.move(keyFolder4, keyFolder3, keyPoint4, PointHierarchyCache.IS_NOT_FOLDER);
		boolean contain = containInCache(cache.getOnBaseParentId(keyFolder3),keyPoint4, PointHierarchyCache.IS_NOT_FOLDER);
		
		assertEquals(true,  contain );
		cache.printTreeInCash("", 0);
		
	}
	
	@Test
	public void movePoint5InToFolder3() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		cache.printTreeInCash("", 0);
		cache.move(keyFolder5, keyFolder3, keyPoint5, PointHierarchyCache.IS_NOT_FOLDER);
		boolean contain = containInCache(cache.getOnBaseParentId(keyFolder3),keyPoint5, PointHierarchyCache.IS_NOT_FOLDER);
		
		assertEquals(true,  contain );
		cache.printTreeInCash("", 0);
		
	}
	
	
	@Test
	public void delete1() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		
		cache.deletePoint(keyFolder3, keyPoint6);
		assertEquals(false, containInCache(cache.getOnBaseParentId(keyFolder3), keyPoint6, PointHierarchyCache.IS_NOT_FOLDER));
		cache.deletePoint(keyFolder5, keyPoint5);
		assertEquals(false, containInCache(cache.getOnBaseParentId(keyFolder5), keyPoint5, PointHierarchyCache.IS_NOT_FOLDER));
		
		assertEquals(true, containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT), keyPoint5, PointHierarchyCache.IS_NOT_FOLDER));
		assertEquals(true, containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT), keyPoint6, PointHierarchyCache.IS_NOT_FOLDER));
	}
	
	@Test
	public void delete2() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		
		cache.deleteFolder(PointHierarchyCache.ROOT, keyFolder5);
		assertEquals(false, containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT), keyFolder5, PointHierarchyCache.IS_FOLDER));
		
		cache.deleteFolder(PointHierarchyCache.ROOT, keyFolder1);
		assertEquals(false, containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT), keyFolder1, PointHierarchyCache.IS_FOLDER));
		
		assertEquals(true, containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT), keyPoint5, PointHierarchyCache.IS_NOT_FOLDER));
		assertEquals(true, containInCache(cache.getOnBaseParentId(keyFolder3), keyPoint6, PointHierarchyCache.IS_NOT_FOLDER));
		assertEquals(true, containInCache(cache.getOnBaseParentId(keyFolder3), keyPoint7, PointHierarchyCache.IS_NOT_FOLDER));
	}
	
	@Test
	public void delete3() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		cache.deleteFolder(keyFolder2, keyFolder3);
		cache.printTreeInCash("", 0);
		assertEquals(false, containInCache(cache.getOnBaseParentId(keyFolder2), keyFolder3, PointHierarchyCache.IS_FOLDER));
		assertEquals(true, containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT), keyPoint6, PointHierarchyCache.IS_NOT_FOLDER));
		assertEquals(true, containInCache(cache.getOnBaseParentId(PointHierarchyCache.ROOT), keyPoint7, PointHierarchyCache.IS_NOT_FOLDER));
	}
	
	@Test
	public void edit() throws Exception {
		addPoints();
		addFolders();
		addPointsInFolder();
		cache.edit(PointHierarchyCache.ROOT, keyFolder1, "newTitle1", PointHierarchyCache.IS_FOLDER);
		cache.edit(keyFolder1, keyFolder2, "newTitle2", PointHierarchyCache.IS_FOLDER);
		cache.edit(keyFolder2, keyFolder3, "newTitle3", PointHierarchyCache.IS_FOLDER);
		cache.edit(PointHierarchyCache.ROOT, keyFolder5, "newTitle5", PointHierarchyCache.IS_FOLDER);
		cache.printTreeInCash("", 0);
		assertEquals("newTitle1", findFolder(cache.getOnBaseParentId(PointHierarchyCache.ROOT), keyFolder1).getTitle());
		assertEquals("newTitle2", findFolder(cache.getOnBaseParentId(keyFolder1), keyFolder2).getTitle());
		assertEquals("newTitle3", findFolder(cache.getOnBaseParentId(keyFolder2), keyFolder3).getTitle());
		assertEquals("newTitle5", findFolder(cache.getOnBaseParentId(PointHierarchyCache.ROOT), keyFolder5).getTitle());
	}
	
	//TODO test wczytanie danych ktore maja wskazanie na nieistniejacy folder
	
	
	
}
