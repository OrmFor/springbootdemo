package com.jlkj.web.shopnew.view;

/**
 * 
 * @Description 版本返回信息
 * @author wzq
 * @date 2018年11月27日 下午6:55:29
 */
public class VersionInfo {

	private String versionCode; // 版本编号
	private String versionName; // 版本号
	private Integer versionLevel; // 版本等级，1：低，2：中，3：高
	private String updateUrl; // 更新地址
	private String content; // 更新内容

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Integer getVersionLevel() {
		return versionLevel;
	}

	public void setVersionLevel(Integer versionLevel) {
		this.versionLevel = versionLevel;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}