package KeywordDriverUtil.Reports;

import KeywordDriverUtil.KeywordUtilities;

public abstract class AbstractReportGenerator implements ReportGenerator{
	private String project;
	private String release;
	
	public AbstractReportGenerator() {
		project = KeywordUtilities.getValueFromConfigProperties("ProjectName");
		release = KeywordUtilities.getValueFromConfigProperties("Release");
	}
	
	protected String getProject(){
		return project;
	}
	
	protected String getRelease(){
		return release;
	}
}
