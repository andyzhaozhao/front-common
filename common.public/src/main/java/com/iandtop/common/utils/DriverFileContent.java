package com.iandtop.common.utils;

import java.io.Serializable;

public class DriverFileContent implements Serializable
{
	private static final long serialVersionUID = -3144001985424492556L;

	public String fileName;
	public byte[] fileContent;

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public byte[] getFileContent()
	{
		return fileContent;
	}

	public void setFileContent(byte[] fileContent)
	{
		this.fileContent = fileContent;
	}

	public DriverFileContent(String fileName, byte[] fileContent)
	{
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
	}

	
}
