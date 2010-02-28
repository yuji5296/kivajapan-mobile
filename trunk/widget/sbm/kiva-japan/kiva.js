//取得したデータをグローバル変数化
var xml;
var style = "normal";
var counter = 0; //通常表示で1件ずつ表示するようのカウンター

// 通常⇔最大表示切替用イベントハンドラ
// function setAttribute(in_dst_element, in_attr_name, in_value){
	// ar attr;
	// attr = document.createAttribute(in_attr_name);
	// attr.nodeValue = in_value;
	// in_dst_element.setAttributeNode(attr);
// }

// 通常表示イベント処理
function test_onRestore() {
    document.getElementById("main_iframe").src = "restore.html";
	style = "normal";
}

// 最大表示イベント処理
function test_onMaximize() {
	document.getElementById("main_iframe").src = "maximize.html";
	style = "maxim";
}

// 割り込みイベント処理
function test_onRestart() {
    location.reload();
}

// イベントハンドラ登録
widget.onrestore = test_onRestore;
widget.onmaximize = test_onMaximize;
widget.onrestart = test_onRestart;


//XMLHttpRequestオブジェクト生成
function createHttpRequest(){

//Win ie用
if(window.ActiveXObject){
	try {
		//MSXML2以降用
		return new ActiveXObject("Msxml2.XMLHTTP") //[1]'
	} catch (e) {
		try {
			//旧MSXML用
			return new ActiveXObject("Microsoft.XMLHTTP") //[1]'
		} catch (e2) {
			return null
		}
	 }
} else if(window.XMLHttpRequest){
	//Win ie以外のXMLHttpRequestオブジェクト実装ブラウザ用
   document.getElementById("debug").innerHTML = "XMLHttpRequest"
	return new XMLHttpRequest() //[1]'
} else {
   document.getElementById("debug").innerHTML = "null"
	return null
}
}

//ファイルにアクセスし受信内容を確認します
function requestFile( data , method , fileName , async )
{
	//XMLHttpRequestオブジェクト生成
	var httpoj = createHttpRequest(); //[1]

	//URLをテキストボックスから取得
	//fileName = document.getElementById("url").value
    //fileName = "atom.xml";
	fileName = "http://kivajapan.jp/?page=Atom";
	//fileName = "https://mail.google.com/mail/feed/atom/?atom_browser_auth"
	//fileName = "https://secure.softbank.ne.jp/yuji5296:yuji727@mail.google.com/mail/feed/atom/"

	//open メソッド
	httpoj.open( method , fileName , async ); //[2]
	document.getElementById("debug").innerHTML = "open";

	//受信時に起動するイベント
	httpoj.onreadystatechange = function()  //[4]
	{ 
	  //readyState値は4で受信完了
	  if (httpoj.readyState==4)  //[5]
	  { 
		//コールバック
		on_loaded(httpoj)
	  }
	}
	document.getElementById("debug").innerHTML = httpoj.readyState

	//send メソッド
	httpoj.send( data ) //[3]
}

function show_detail(id){
	document.getElementById('div_'+id).style.visibility = "visible";
	document.getElementById('list').style.visibility = "hidden";
	//document.getElementById('output').style.visibility = "hidden";
	//alert(id);
}
//IDで指定された1件の詳細情報を表示する
function show_detail2(id)
{
		
	//エントリー数の取得
	number = xml.getElementsByTagName("entry").length;
	document.getElementById("debug").innerHTML = number ;

	//タイトル情報取得
	text = xml.getElementsByTagName('title')[0].firstChild.nodeValue;
	
	//データ取得
	list = "<ul>";
	detail = "";//"<div style='position:absolute;top:0px;width:100%;'>";
	for (i = 0; i < number; i++) {
		item  = xml.getElementsByTagName("entry")[i];
		//document.getElementById("output").innerHTML = item ;
		if (id == item.getElementsByTagName('id')[0].firstChild.nodeValue){ 
			title = item.getElementsByTagName('title')[0].firstChild.nodeValue; 
			href = item.getElementsByTagName('link')[0].getAttribute("href"); 
			content	= item.getElementsByTagName('content')[0].firstChild.nodeValue; //[6]
			//詳細表示
			detail = detail + "<div id='" + href + "' onclick='show_detail3(id)' style='position:absolute;top:0px;visibility:visible;'><b>" + title + "</b>" + "<p>" + content + "</p></div>" ;
		}	
    }
	
	//詳細情報を表示
	document.getElementById("detail").innerHTML = detail ;
	document.getElementById('list').style.visibility = "hidden";
	//document.getElementById('output').style.visibility = "hidden";
	document.getElementById('detail').style.visibility = "visible";
	
}
//詳細情報をネイティブブラウザで表示する
//idにはURLを指定
function show_detail3(id){
	widget.openURL(id);
}
//通常画面で最新のentryのtitleを表示する
function show_newest()
{
	//エントリー数の取得
	number = xml.getElementsByTagName("entry").length;
	
	//１周表示し終わったら最初に戻る
	if ( counter >= number ) counter = 0;
	
	item  = xml.getElementsByTagName("entry")[counter];
	title = item.getElementsByTagName('title')[0].firstChild.nodeValue; 
	list = "<div>" + title + "</div>";

	//表示
	document.getElementById("newest").innerHTML = list ;
	
	//タイマーセット
	timerID = setTimeout("show_newest()", 2000);
	
	//次のアイテムの表示する
	counter = counter + 1;
}

//一覧を表示する
function show_list()
{

	//エントリー数の取得
	number = xml.getElementsByTagName("entry").length;
	document.getElementById("debug").innerHTML = number ;

	//タイトル情報取得
	maintitle = xml.getElementsByTagName('title')[0].firstChild.nodeValue;
	subtitle = xml.getElementsByTagName('subtitle')[0].firstChild.nodeValue ;
	updated =  xml.getElementsByTagName('updated')[0].firstChild.nodeValue ;
	list = maintitle + "[" + updated + "]" + "<marquee>" + subtitle + "</marquee>";
	
	//データ取得
	list = list + "<ul>";
	//detail = "";//"<div style='position:absolute;top:0px;width:100%;'>";
	for (i = 0; i < number; i++) {
		item  = xml.getElementsByTagName("entry")[i];
		//document.getElementById("output").innerHTML = item ;
		title = item.getElementsByTagName('title')[0].firstChild.nodeValue; 
		id = item.getElementsByTagName('id')[0].firstChild.nodeValue; 
		//href = item.getElementsByTagName('link')[0].getAttribute("href"); 
		//content	= item.getElementsByTagName('content')[0].firstChild.nodeValue; //[6]
		//一覧表示
		list = list + "<li><a id=" + id + " onclick='show_detail2(id);'>" + title + "</a></li>";
		//詳細表示
		//detail = detail + "<div id='div_" + id + "' style='position:absolute;top:0px;visibility:hidden;width:100%;'><b><a href=" + href + ">" + title + "</a></b>" + "<p>" + content + "</p></div>"
    }
	list = list + "</ul>";
	//detail = detail + "</div>";

	//text = text + list + detail;
	
	//document.getElementById("output").innerHTML = text ;
	document.getElementById("list").innerHTML = list ;
	//document.getElementById("detail").innerHTML = detail ;
	document.getElementById('list').style.visibility = "visible";
	//document.getElementById('output').style.visibility = "visible";
	document.getElementById('detail').style.visibility = "hidden";
	//document.getElementById('help').style.visibility = "hidden";
	
}

//コールバック関数 ( 受信時に実行されます )
function on_loaded(oj)
{
	//レスポンスを取得
	xml  = oj.responseXML.documentElement;
	//text = oj.responseText;
	//document.getElementById("debug").innerHTML = text ;
	//document.getElementById("debug").innerHTML = xml ;	
	
	if (style == "normal") {
		show_newest();
	}else {
		show_list();
	}
}

function reload(){
	requestFile( '' , 'GET', '' , true );
}

//コンテンツ切替用イベントハンドラ
function help(){
	document.getElementById("body").src = "help.html";
}
function rsslist(){
	document.getElementById("body").src = "rsslist.html";
}
