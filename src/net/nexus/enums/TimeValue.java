package net.nexus.enums;

public enum TimeValue {

	DAY(1), WEEK(7), MONTH(30), PERMA(99999);
	
	public int duration;
	
	private TimeValue(int duration) {
		this.duration = duration;
	}
	
}
