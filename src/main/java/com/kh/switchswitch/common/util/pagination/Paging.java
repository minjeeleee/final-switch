package com.kh.switchswitch.common.util.pagination;

import lombok.Getter;

@Getter
public class Paging {
	
	//입력받을 값
	private String type; //페이징 처리할 항목
	private int cuurentPage; //현재 페이지
	private int total; //전체 게시물 수
	private int cntPerPage; // 페이지당 게시물 수
	private int blockCnt; //하단에 표시될 페이지블록 개수
	private String sort; //정렬 기준 컬럼
	private String direction; //정렬 방향
	
	//계산할 값
	private int blockStart; //시작 블록
	private int blockEnd; //마지막 블록
	private int lastPage; //마지막 페이지
	private int prev; //이전 버튼
	private int next; //다음 버튼
	
	//쿼리 between문에서 사용할 rownum 범위
	private int queryStart;
	private int queryEnd;
	
	private Paging(PagingBuilder builder) {
		this.type = builder.type;
		this.cuurentPage = builder.cuurentPage;
		this.total = builder.total;
		this.cntPerPage = builder.cntPerPage;
		this.blockCnt = builder.blockCnt;
		this.sort = builder.sort;
		this.direction = builder.direction;
		
		calBlockStartAndEnd();
		calQueryStartAndEnd();
		calPrevAndNext();
	}
	
	public static PagingBuilder builder() {
		return new PagingBuilder();
	}
	
	private void calLastPage() {
		lastPage = (int) ((total-1)/(double)cntPerPage) + 1 ;
	}
	
	private void calBlockStartAndEnd() {
		calLastPage(); //마지막 페이지 계산
		
		blockStart = ((cuurentPage - 1)/ blockCnt) * blockCnt + 1;
		blockEnd = blockStart + blockCnt - 1;
		
		blockStart = blockStart < 1? 1:blockStart;
		blockEnd = blockEnd > lastPage? lastPage:blockEnd;
	}
	
	private void calQueryStartAndEnd() {
		queryEnd = cuurentPage * cntPerPage;
		queryStart = queryEnd - cntPerPage + 1;
	}
	
	private void calPrevAndNext() {
		prev = cuurentPage == 1? 1 : cuurentPage -1;
		next = cuurentPage == lastPage?  lastPage : cuurentPage + 1;
	}
	
	public static class PagingBuilder{
		//입력받을 값
		private String type; //페이징 처리할 항목
		private int cuurentPage; //현재 페이지
		private int total; //전체 게시물 수
		private int cntPerPage; // 페이지당 게시물 수
		private int blockCnt; //하단에 표시될 페이지블록 개수
		private String sort;
		private String direction;
		
		public PagingBuilder type(String val) {
			this.type = val;
			return this;
		}
		
		public PagingBuilder sort(String val) {
			this.sort = val;
			return this;
		}
		
		public PagingBuilder direction(String val) {
			this.direction = val;
			return this;
		}
		
		public PagingBuilder cuurentPage(int val) {
			this.cuurentPage = val;
			return this;
		}
		
		public PagingBuilder total(int val) {
			this.total = val;
			return this;
		}
		
		public PagingBuilder cntPerPage(int val) {
			this.cntPerPage = val;
			return this;
		}
		
		public PagingBuilder blockCnt(int val) {
			this.blockCnt = val;
			return this;
		}
		
		public Paging build() {
			return new Paging(this);
		}
	}

}
