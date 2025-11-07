package constants;

public class Constant {

	public static final String SYSTEM = "SYSTEM";

	public enum STATUS_CUSTOMER {
		ACTIVE("active", 1), INACTIVE("inactive", 0), SUBMISSION("submission", 2), ONREVIEW("onreview", 3);

		private String desc;
		private Integer value;

		private STATUS_CUSTOMER(String desc, Integer value) {
			this.desc = desc;
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public Integer getValue() {
			return value;
		}
	}

	public enum STATUS_SEND {
		ONPROGRESS("onprogress", 0), FINISH("finish", 1), SUCCESSFUL_SEND("successful send", 2),
		SUCCESSFUL_RECEIVED("successful received", 3), FAILED("failed", 4), CORRUPTED("corrupt", 5);

		private String desc;
		private Integer value;

		private STATUS_SEND(String desc, Integer value) {
			this.desc = desc;
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public Integer getValue() {
			return value;
		}
	}
}
