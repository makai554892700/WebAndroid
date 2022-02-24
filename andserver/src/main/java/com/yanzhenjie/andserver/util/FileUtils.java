/*
 * Copyright © 2017 Yan Zhenjie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.andserver.util;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

/**
 * Created by YanZhenjie on 2017/12/19.
 */
public class FileUtils {

    /**
     * Get MimeType of file path.
     *
     * @param path file path.
     * @return get contentType based on file name, if not {@code application/octet-stream}.
     */
    public static String getMimeType(String path) {
        String mimeType = "application/octet-stream";
        Log.e("-----1", "getMimeType path=" + path);
        if (!TextUtils.isEmpty(path)) {
            if (path.endsWith(".js")) {
                mimeType = "text/javascript";
            } else {
                String extension = MimeTypeMap.getFileExtensionFromUrl(path);
                if (MimeTypeMap.getSingleton().hasExtension(extension)) {
                    mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                }
            }
        }
        return mimeType;
    }

}