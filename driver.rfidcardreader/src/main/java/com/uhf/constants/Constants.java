package com.uhf.constants;

public class Constants {
	
	public enum Result {
		/* Success */
		STATUS_OK(0),
		/* Attempted to open a radio that is already open */
		ERROR_ALREADY_OPEN(-9999), /* -9999 */
		/* Buffer supplied is too small */
		ERROR_BUFFER_TOO_SMALL(-9998), /* -9998 */
		/* General failure */
		ERROR_FAILURE(-9997), /* -9997 */
		/* Failed to load radio bus driver */
		ERROR_DRIVER_LOAD(-9996), /* -9996 */
		/* Library cannot use version of radio bus driver present on system */
		ERROR_DRIVER_MISMATCH(-9995), /* -9995 */
		/*
		 * This error code is no longer used, maintain slot in enum in case
		 * anyone is using hard-coded error codes for some reason
		 */
		ERROR_RESERVED_01(-9994), /* -9994 */
		/* Antenna number is invalid */
		ERROR_INVALID_ANTENNA(-9993), /* -9993 */
		/* Radio handle provided is invalid */
		ERROR_INVALID_HANDLE(-9992), /* -9992 */
		/* One of the parameters to the function is invalid */
		ERROR_INVALID_PARAMETER(-9991), /* -9991 */
		/* Attempted to open a non-existent radio */
		ERROR_NO_SUCH_RADIO(-9990), /* -9990 */
		/* Library has not been successfully initialized */
		ERROR_NOT_INITIALIZED(-9989), /* -9989 */
		/* Function not supported */
		ERROR_NOT_SUPPORTED(-9989), /* -9988 */
		/* Operation was cancelled by call to cancel operation, close radio, or */
		/* shut down the library */
		ERROR_OPERATION_CANCELLED(-9987), /* -9987 */
		/* Library encountered an error allocating memory */
		ERROR_OUT_OF_MEMORY(-9986), /* -9986 */
		/* The operation cannot be performed because the radio is currently busy */
		ERROR_RADIO_BUSY(-9985), /* -9985 */
		/* The underlying radio module encountered an error */
		ERROR_RADIO_FAILURE(-9984), /* -9984 */
		/* The radio has been detached from the system */
		ERROR_RADIO_NOT_PRESENT(-9983), /* -9983 */
		/* The RFID library function is not allowed at this time. */
		ERROR_CURRENTLY_NOT_ALLOWED(-9982), /* -9982 */
		/* The radio module's MAC firmware is not responding to requests. */
		ERROR_RADIO_NOT_RESPONDING(-9981), /* -9981 */
		/*
		 * The MAC firmware encountered an error while initiating the
		 * nonvolatile
		 */
		/* memory update. The MAC firmware will return to its normal idle state */
		/* without resetting the radio module. */
		ERROR_NONVOLATILE_INIT_FAILED(-9980), /* -9980 */
		/* An attempt was made to write data to an address that is not in the */
		/* valid range of radio module nonvolatile memory addresses. */
		ERROR_NONVOLATILE_OUT_OF_BOUNDS(-9979), /* -9979 */
		/* The MAC firmware encountered an error while trying to write to the */
		/* radio module's nonvolatile memory region. */
		ERROR_NONVOLATILE_WRITE_FAILED(-9978), /* -9978 */
		/* The underlying transport layer detected that there was an overflow */
		/* error resulting in one or more bytes of the incoming data being */
		/* dropped. The operation was aborted and all data in the pipeline was */
		/* flushed. */
		ERROR_RECEIVE_OVERFLOW(-9977), /* -9977 */
		/* An unexpected value was returned to this function by the MAC firmware */
		ERROR_UNEXPECTED_VALUE(-9976), /* -9976 */
		/* The MAC firmware encountered CRC errors while trying to */
		/* write to the radio module's nonvolatile memory region. */
		ERROR_NONVOLATILE_CRC_FAILED(-9975), /* -9975 */
		/* The MAC firmware encountered unexpected values in the packet header */
		ERROR_NONVOLATILE_PACKET_HEADER(-9974), /* -9974 */
		/* The MAC firmware received more than the specified maximum packet size */
		ERROR_NONVOLATILE_MAX_PACKET_LENGTH(-9973), /* -9973 */

		// malloc memory Failed
		ACTION_REQUEST_MEMORY_FAILED(-20000),
		// search radio ,and find no radio can be found
		ACTION_NO_RADIO_LINK(-19999), /* 19999 */
		// Get radio infomation failed
		ACTION_RETRIEVE_ATTACHE_FAILED(-19998), /* 19998 */
		// open or close radio the serial num out of hold
		ACTION_ERROR_OUT_RADIO_COUNT(-19997), /* 19997 */
		// Attempted to close a radio that is already close
		// ERROR_ALREADY_OPEN
		ACTION_ERROR_RADIO_ALREADY_CLOSE(-1996), /* 19996 */
		// when read tag flag is 2
		ACTION_RFID_18K6C_TAG_ACCESS_BACKSCATTER_ERROR(-19995), /* 19995 */
		// when read tag mac throw an error
		ACTION_RFID_18K6C_TAG_ACCESS_MAC_ERROR(-19994), /* 19994 */
		// Read Tag No Data
		ACTION_RFID_18K6C_TAG_READ_NON_DATA(-19993), /* 19993 */
		// Write data do not match
		ACTION_RFID_18K6C_TAG_WRITE_ERROR_DATA(-19992), /* 19992 */
		// read tag failed
		ACTION_RID_18K6C_TAG_READ_FAILED(-19991), /* 19991 */
		// ACTION
		ACTION_RFID_18K6C_TAG_WRITE_FAILED(-19990), /* 19990 */
		// when read tag password ,read data wrong ,throw this error
		ACTION_RFID_18K6C_GET_TAG_PASSWORD_FAILED(-19989), /* 19989 */
		// password error
		ACTION_RFID_18K6C_KILL_TAG_PWD_ERROR(-19988), /* 19988 */
		//
		ACTION_RFID_18K6C_KILL_TAG_ERROR(-19987), /* 19987 */
		// permission
		ACTION_RFID_18K6C_SET_PERMISSION_ERROR(-19986), /* 19986 */
		//
		ACTION_RFID_HAVE_INIT(-19985), /* 19985 */
		/* The MAC firmware received a error packet infomation */
		ERROR_WRONG_PACKET(-19972);/* -9972 */
		private int result;

		private Result(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum CountryRegion
	{
		CHINA(0),
		USA(1),
		OPEN_AREA(2);
		private int result;
		private CountryRegion(int result) {
			this.result = result;
		}
		public int getValue() {
			return result;
		}
	}

	public enum MacRegion {
		China840_845(0), China920_925(1), Open_Area902_928(2);
		private int result;

		private MacRegion(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum InventoryMode {
		HighSpeedMode(0), IntelligentMode(1), LowPowerMode(2), UserDefined(3);
		private int result;

		private InventoryMode(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum MemoryBank {
		RESERVED(0), EPC(1), TID(2), USER(3);
		private int result;

		private MemoryBank(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum PasswordPermission {
		ACCESSIBLE(0), ALWAYS_ACCESSIBLE(1), SECURED_ACCESSIBLE(2), ALWAYS_NOT_ACCESSIBLE(
				3), NO_CHANGE(4);
		private int result;

		private PasswordPermission(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum MemoryPermission {
		WRITEABLE(0), ALWAYS_WRITEABLE(1), SECURED_WRITEABLE(2), ALWAYS_NOT_WRITEABLE(
				3), NO_CHANGE(4);
		private int result;

		private MemoryPermission(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum AntennaPortState {
		DISABLED(0), ENABLED(1);
		private int result;

		private AntennaPortState(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum SingulationAlgorithm {
		FIXEDQ(0), DYNAMICQ(
				1);
		private int result;

		private SingulationAlgorithm(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum ConnectMode {
		USB(0), UART(1);
		private int result;

		private ConnectMode(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum ResponseType {
		DATA(0xFFFFFFFF);
		private int result;

		private ResponseType(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum ResponseMode {
		COMPACT(0x00000001),
		NORMAL(0x00000003),
		EXTENDED(0x00000007);
		private int result;

		private ResponseMode(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}
}
