package jp.kivajapan.rssreader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

//URI�ǻ��ꤷ���������ɤ߹���ImageView��ɽ�����롣
public class DownloadTask extends AsyncTask<String, Void, Bitmap> {  
    // ���������ɽ������ӥ塼  
    private ImageView imageView;  
  
    // ���󥹥ȥ饯��  
    public DownloadTask(ImageView imageView) {
        this.imageView = imageView;
    	// �ץ��쥹������ɽ���ʹ�������ɽ������٤ˤ����������
        this.imageView.setImageResource(R.drawable.progresscircle_small);

    	// ���˥᡼�����꥽���������
        Animation anim = AnimationUtils.loadAnimation(this.imageView.getContext(), R.anim.progresscircle);
    	anim.setRepeatMode(Animation.RESTART);

    	// ���˥᡼����󳫻�
    	this.imageView.startAnimation(anim);

    }  
    @Override  
	protected void onPreExecute() {
    	
	}

    // �Хå����饦��ɤǼ¹Ԥ������  
    @Override  
    protected Bitmap doInBackground(String... urls) {  

    	// �ԥå����å׵��ȲȤμ̿���ɽ������
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(new HttpGet(urls[0]));
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Bitmap bitm = null;
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream in = null;
			try {
				in = httpEntity.getContent();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bitm = BitmapFactory.decodeStream(in);
		}

		return bitm;  
    }  
  
    // �ᥤ�󥹥�åɤǼ¹Ԥ������  
    @Override  
    protected void onPostExecute(Bitmap result) {  
    	this.imageView.clearAnimation();
    	this.imageView.setImageBitmap(result);  
		this.imageView.invalidate();
    }

}  