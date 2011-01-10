package jp.kivajapan.rssreader;

public class Item {
	// �����Υ����ȥ�
	private CharSequence mTitle;
	// ��������ʸ
	private CharSequence mDescription;
	// ����������
	private CharSequence mSummary;
	// ��������ʸ
	private CharSequence mContent;
	// ������
	private CharSequence mAuthor;
	// ����
	private CharSequence mImage;

	public Item() {
		mTitle = "";
		mDescription = "";
		mSummary = "";
		mContent = "";
		mAuthor = "";
	}

	public CharSequence getDescription() {
		return mDescription;
	}
	public void setDescription(CharSequence description) {
		mDescription = description;
	}

	//�����ȥ�μ���������
	public CharSequence getTitle() {
		return mTitle;
	}
	public void setTitle(CharSequence title) {
		mTitle = title;
	}

	//����μ���������
	public CharSequence getSummary() {
		return mSummary;
	}
	public void setSummary(CharSequence summary) {
		mSummary = summary;
	}

	//��ʸ�μ���������
	public CharSequence getContent() {
		return mContent;
	}
	public void setContent(CharSequence content) {
		mContent = content;
	}

	//�����Ԥμ���������
	public CharSequence getAuthor() {
		return mAuthor;
	}
	public void setAuthor(CharSequence author) {
		mAuthor = author;
	}

	//�����μ���������
	public CharSequence getImage() {
		return mImage;
	}
	public void setImage(CharSequence image) {
		mImage = image;
	}

}
