package com.uhf.structures;

public class TagGroup {
	public int selected;
	public int session;
	public int target;
	public TagGroup(){};
	public TagGroup(int selected,int session,int target){
		this.selected = selected;
		this.session = session;
		this.target = target;
	}
	public void setValue(int selected,int session,int target){
		this.selected = selected;
		this.session = session;
		this.target = target;
	}
}
