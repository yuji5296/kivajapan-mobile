package jp.kivajapan.rssreader;

import android.graphics.Bitmap;

public class Item {
	// 記事のタイトル
	private String mTitle;
	// URL
	private String mLink;
	// 記事の本文
//	private String mDescription;
	// 記事の要約
	private String mSummary;
	// 記事の本文
	private String mContent;
	// 翻訳者
	private String mAuthor;
	// 画像URL
	private String mImage;
	// 画像データ
	private byte[] mBmp; 

	public Item() {
		mTitle = "";
		mLink = "";
//		mDescription = "";
		mSummary = "";
		mContent = "";
		mAuthor = "";
		mImage = "";
		mBmp = null;
	}

//	public String getDescription() {
//		return mDescription;
//	}
//	public void setDescription(String description) {
//		mDescription = description;
//	}

	//タイトルの取得・設定
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String title) {
		mTitle = title;
	}

	//URLの取得・設定
	public String getLink() {
		return mLink;
	}
	public void setLink(String link) {
		mLink = link;
	}

	
	//要約の取得・設定
	public String getSummary() {
		return mSummary;
	}
	public void setSummary(String summary) {
		mSummary = summary;
	}

	//本文の取得・設定
	public String getContent() {
		return mContent;
	}
	public void setContent(String content) {
		mContent = content;
	}

	//翻訳者の取得・設定
	public String getAuthor() {
		return mAuthor;
	}
	public void setAuthor(String author) {
		mAuthor = author;
	}

	//画像URLの取得・設定
	public String getImage() {
		return mImage;
	}
	public void setImage(String image) {
		mImage = image;
	}
	//画像データの取得・設定
	public byte[] getBmp() {
		return mBmp;
	}
	public void setBmp(byte[] bmp) {
		mBmp = bmp;
	}

}
