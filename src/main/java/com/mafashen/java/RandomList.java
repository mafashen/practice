package com.mafashen.java;

import org.junit.Test;

import java.util.*;

public class RandomList {

	public static void main(String[] args){
		System.out.println(4000%5000);
		List list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		System.out.println(list.subList(0 , 2) + "  " + list.subList(2 , 4));

		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				Random random = new Random();
				return random.nextInt(10000) - random.nextInt(10000);
			}
		});
		for (Object o : list) {
			System.out.println(o);
		}
	}

	@Test
	public void testThreadLocal(){
		ThreadLocal<List<String>> list = new ThreadLocal<List<String>>();
//		list.set(new ArrayList<String>());
//		list.get().add("123");
//		list.get().add("123");
//		System.out.println(list.get());
		list.remove();
	}

	@Test
	public void testEncode() throws Exception{
		String str = "?????????,???";
		String s = new String(str.getBytes("utf-8"));
//		System.out.println(s);
		System.out.println(System.currentTimeMillis());
	}

	@Test
	public void testRandom(){
		Random random = new Random();
		StringBuilder barcode = new StringBuilder();
		for (int i = 0 ; i < 8 ; i++){
			barcode.append(random.nextInt(10));
		}
		System.out.println(barcode.toString() + "   length:  " + barcode.length());
	}

	@Test
	public void testSort(){
		Map<Long , List<String>> map = new HashMap<>();

		List<IdMapDO> list = new ArrayList<>();//idMapDAO.queryOuterIds(spuIds, null, MapType.spu, PlatformEnum.JD.getId());
		list.add(new IdMapDO(514762L , 9L , "2011223605"));
		list.add(new IdMapDO(514762L , 10L , "2011213607"));
		list.add(new IdMapDO(514762L , 12L , "2011213608"));
		list.add(new IdMapDO(514448L , 10L , "74048"));
		list.add(new IdMapDO(514448L , 12L , "315600"));
		list.add(new IdMapDO(512950L , 12L , "74048"));
		list.add(new IdMapDO(514765L , 9L , "74044"));
		list.add(new IdMapDO(514765L , 10L , "74045"));
		list.add(new IdMapDO(514765L , 11L , "74046"));
		list.add(new IdMapDO(514765L , 12L , "74047"));

		if (list != null && !list.isEmpty()){
			Map<Long , Map<Long , String>> sortMap = new HashMap<>();
			for (IdMapDO idMapDO : list) {
				if (!sortMap.containsKey(idMapDO.getInnerId())) {
					sortMap.put(idMapDO.getInnerId(), new TreeMap<Long, String>());
				}
				if (idMapDO.getOuterId() != null) {
					sortMap.get(idMapDO.getInnerId()).put(idMapDO.getInnerShopId(), idMapDO.getOuterId());
				}
			}
			List<Long> auths = Arrays.asList(9L, 10L ,11L, 12L);
			for (Long aLong : sortMap.keySet()) {
				for (Long innerShop : auths) {
					if (!sortMap.get(aLong).containsKey(innerShop)) {
						sortMap.get(aLong).put(innerShop, "0");
					}
				}
				map.put(aLong, new ArrayList<String>(sortMap.get(aLong).values()));
			}
		}
		System.out.println(map);
	}

	class IdMapDO {
		private Long id;
		private String innerBizIdentifier;
		private Long innerId;
		private Long innerShopId;
		private Integer isp;
		private String outerBizIdentifier;
		private String outerId;
		private String outerShopId;
		private Integer type;
		private Integer status;
		private Integer enabled;
		private String attributes;
		private Date gmtCreate;
		private Date gmtModify;

		public IdMapDO(Long innerId, Long innerShopId, String outerId) {
			this.innerId = innerId;
			this.innerShopId = innerShopId;
			this.outerId = outerId;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getInnerBizIdentifier() {
			return innerBizIdentifier;
		}

		public void setInnerBizIdentifier(String innerBizIdentifier) {
			this.innerBizIdentifier = innerBizIdentifier;
		}

		public Long getInnerId() {
			return innerId;
		}

		public void setInnerId(Long innerId) {
			this.innerId = innerId;
		}

		public Long getInnerShopId() {
			return innerShopId;
		}

		public void setInnerShopId(Long innerShopId) {
			this.innerShopId = innerShopId;
		}

		public Integer getIsp() {
			return isp;
		}

		public void setIsp(Integer isp) {
			this.isp = isp;
		}

		public String getOuterBizIdentifier() {
			return outerBizIdentifier;
		}

		public void setOuterBizIdentifier(String outerBizIdentifier) {
			this.outerBizIdentifier = outerBizIdentifier;
		}

		public String getOuterId() {
			return outerId;
		}

		public void setOuterId(String outerId) {
			this.outerId = outerId;
		}

		public String getOuterShopId() {
			return outerShopId;
		}

		public void setOuterShopId(String outerShopId) {
			this.outerShopId = outerShopId;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Integer getEnabled() {
			return enabled;
		}

		public void setEnabled(Integer enabled) {
			this.enabled = enabled;
		}

		public String getAttributes() {
			return attributes;
		}

		public void setAttributes(String attributes) {
			this.attributes = attributes;
		}

		public Date getGmtCreate() {
			return gmtCreate;
		}

		public void setGmtCreate(Date gmtCreate) {
			this.gmtCreate = gmtCreate;
		}

		public Date getGmtModify() {
			return gmtModify;
		}

		public void setGmtModify(Date gmtModify) {
			this.gmtModify = gmtModify;
		}

	}
}
