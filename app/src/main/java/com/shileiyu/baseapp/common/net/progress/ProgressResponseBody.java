package com.shileiyu.baseapp.common.net.progress;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

import static android.content.ContentValues.TAG;

/**
 * @author shilei.yu
 * @since on 2018/3/28.
 */

public class ProgressResponseBody extends ResponseBody {
    private ResponseBody real;
    private String url;
    private BufferedSource bufferedSource;
    private ProgressListener listener = null;

    public ProgressResponseBody(ResponseBody real, String url) {
        this.real = real;
        this.url = url;
        listener = ProgressInterceptor.MAP.get(url);
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return real.contentType();
    }

    @Override
    public long contentLength() {
        return real.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(real.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource {

        long totalBytesRead = 0;

        int currentProgress;

        ProgressSource(Source source) {
            super(source);
        }

        @Override
        public long read(@NonNull Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            long fullLength = real.contentLength();
            if (bytesRead == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }
            int progress = (int) (100f * totalBytesRead / fullLength);
            Log.d(TAG, "download progress is " + progress);
            if (listener != null && progress != currentProgress) {
                listener.onProgress(progress);
            }
            if (listener != null && totalBytesRead == fullLength) {
                listener = null;
            }
            currentProgress = progress;
            return bytesRead;
        }
    }
}
