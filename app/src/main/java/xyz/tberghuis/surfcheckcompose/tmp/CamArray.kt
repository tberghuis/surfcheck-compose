package xyz.tberghuis.surfcheckcompose.tmp

data class Cam(
  val name: String,
  val url: String
)


val camArray = arrayOf<Cam>(
  Cam("the wreck", "https://cams.cdn-surfline.com/cdn-au/au-byronbay/playlist.m3u8"),
  Cam("the pass", "https://cams.cdn-surfline.com/cdn-au/au-thepassoverview/playlist.m3u8"),
  Cam("lennox", "https://cams.cdn-surfline.com/cdn-au/au-lennoxhead/playlist.m3u8"),
  Cam("shelly", "https://cams.cdn-surfline.com/cdn-au/au-ballinashellybeach/playlist.m3u8"),
  Cam("northwall", "https://cams.cdn-surfline.com/cdn-au/au-ballinalighthouse/playlist.m3u8"),
  Cam("evans", "https://cams.cdn-surfline.com/cdn-au/au-evanshead/playlist.m3u8"),
)