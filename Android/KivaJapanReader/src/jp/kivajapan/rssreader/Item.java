package jp.kivajapan.rssreader;

public class Item {
	// 記事のタイトル
	private CharSequence mTitle;
	// URL
	private CharSequence mLink;
	// 記事の本文
//	private CharSequence mDescription;
	// 記事の要約
	private CharSequence mSummary;
	// 記事の本文
	private String mContent;
	// 翻訳者
	private CharSequence mAuthor;
	// 画像
	private CharSequence mImage;

	public Item() {
		mTitle = "";
		mLink = "";
//		mDescription = "";
		mSummary = "";
		mContent = "";
		mAuthor = "";
		mImage = "";
	}

//	public CharSequence getDescription() {
//		return mDescription;
//	}
//	public void setDescription(CharSequence description) {
//		mDescription = description;
//	}

	//タイトルの取得・設定
	public CharSequence getTitle() {
		return mTitle;
	}
	public void setTitle(CharSequence title) {
		mTitle = title;
	}

	//URLの取得・設定
	public CharSequence getLink() {
		return mLink;
	}
	public void setLink(CharSequence link) {
		mLink = link;
	}

	
	//要約の取得・設定
	public CharSequence getSummary() {
		return mSummary;
	}
	public void setSummary(CharSequence summary) {
		mSummary = summary;
	}

	//本文の取得・設定
	public CharSequence getContent() {
		return mContent;
	}
	public void setContent(String content) {
		mContent = content;
	}

	//翻訳者の取得・設定
	public CharSequence getAuthor() {
		return mAuthor;
	}
	public void setAuthor(CharSequence author) {
		mAuthor = author;
	}

	//画像の取得・設定
	public CharSequence getImage() {
		return mImage;
	}
	public void setImage(CharSequence image) {
		mImage = image;
	}

}
