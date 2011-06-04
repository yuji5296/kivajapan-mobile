package jp.kivajapan.rssreader;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RssListAdapter extends ArrayAdapter<Item> {
	private LayoutInflater mInflater;
	private TextView mTitle;
	private TextView mDescr;
	private ImageView mImage;

	public RssListAdapter(Context context, List<Item> objects) {
		super(context, 0, objects);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// 1�Ԥ��ȤΥӥ塼����������
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (convertView == null) {
			view = mInflater.inflate(R.layout.item_row, null);
		}

		// ���߻��Ȥ��Ƥ���ꥹ�Ȥΰ��֤���Item���������
		Item item = this.getItem(position);
		if (item != null) {
			// Item����ɬ�פʥǡ�������Ф������줾��TextView�˥��åȤ���
			// �����ȥ�μ���
			String title = item.getTitle().toString();
			mTitle = (TextView) view.findViewById(R.id.item_title);
			mTitle.setText(title);
			
			// �����Ԥμ���
			String author = "�����ԡ�" + item.getAuthor().toString();
			mTitle = (TextView) view.findViewById(R.id.item_author);
			mTitle.setText(author);
			
			// ����μ���
//			String descr = item.getDescription().toString();
			String summary = item.getSummary().toString();
			mDescr = (TextView) view.findViewById(R.id.item_descr);
			mDescr.setText(summary);

			// �����μ���
			// �����Ԥμ̿���RSS�����������ɽ�����褦�Ȥ�����img������src������Ǥ��ʤ� 
			String image = item.getImage().toString();
//			String image = "http://www.kiva.org/img/w80h80/674364.jpg";
//			Uri uri = Uri.parse(image);
			mImage = (ImageView) view.findViewById(R.id.item_image);
	    	DownloadTask task = new DownloadTask(mImage);  
			task.execute(image);

		}
		return view;
	}

}
