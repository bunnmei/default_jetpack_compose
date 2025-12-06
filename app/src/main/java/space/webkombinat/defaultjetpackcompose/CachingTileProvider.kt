package space.webkombinat.defaultjetpackcompose

import android.R.attr.height
import android.R.attr.width
import androidx.collection.LruCache
import com.google.android.gms.maps.model.Tile
import com.google.android.gms.maps.model.UrlTileProvider
import java.net.URL

class CachingTileProvider(
    private val baseUrl: String,
    private val memoryCache: LruCache<String, ByteArray> // メモリキャッシュとしてLruCacheを使用
) : UrlTileProvider(256, 256) {

    // タイルのキーを生成するヘルパー関数
    private fun getTileKey(x: Int, y: Int, zoom: Int) = "$zoom/$x/$y"

    @Synchronized
    override fun getTileUrl(x: Int, y: Int, zoom: Int): URL? {
        // 標準の URL を返す
        return URL("$baseUrl/$zoom/$x/$y.png")
    }

    // ★★★ 最も重要なオーバーライドポイント ★★★
    // 実際にタイルデータを取得するメソッドをオーバーライドします
//    override fun getTile(x: Int, y: Int, zoom: Int): Tile? {
//        val key = getTileKey(x, y, zoom)
//
//        // 1. まずキャッシュを確認
//        val cachedData = memoryCache[key]
//        if (cachedData != null) {
//            return Tile(width, height, cachedData)
//        }
//
//        // 2. キャッシュにない場合はネットワークから取得
//        val url = getTileUrl(x, y, zoom) ?: return null
//        return try {
//            val data = url.readBytes()
//            // 3. 取得したデータをキャッシュに保存
//            memoryCache.put(key, data)
//            Tile(width, height, data)
//        } catch (e: Exception) {
//            // エラー処理
//            null
//        }
//    }
}