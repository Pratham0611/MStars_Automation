package KeywordDriverUtil.Reports;

import KeywordDriverUtil.KeywordUtilities;

public class ReportGeneratorFactory {

	private static enum REPORT_TYPE {HTML, JQGRID};
	private static REPORT_TYPE TYPE = REPORT_TYPE.HTML;
	
	private static ReportGeneratorFactory factory = new ReportGeneratorFactory();
	/**
	 * 
	 */
	private ReportGeneratorFactory() {
		String rptType = KeywordUtilities.getValueFromConfigProperties("report.type");
		if(rptType.length() > 0){
			if(rptType.equalsIgnoreCase("JqGrid")){
				TYPE = REPORT_TYPE.JQGRID;
			}
		}
	}
	
	public static ReportGeneratorFactory getInstance(){
		return factory;
	}
	
	public ReportGenerator getReportGenerator(){
		ReportGenerator rptGen;
		switch(TYPE){
			case JQGRID: rptGen =  new JqGridReportGenerator();
						 break;
			default: rptGen = new HtmlReportGenerator();
		}
		return rptGen;
	}
	
	public boolean isJqGridReport(){
		return (TYPE == REPORT_TYPE.JQGRID);
	}

}

