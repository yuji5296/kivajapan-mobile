//�擾�����f�[�^���O���[�o���ϐ���
var xml;
var style = "normal";
var counter = 0; //�ʏ�\����1�����\������悤�̃J�E���^�[

// �ʏ�̍ő�\���ؑ֗p�C�x���g�n���h��
// function setAttribute(in_dst_element, in_attr_name, in_value){
	// ar attr;
	// attr = document.createAttribute(in_attr_name);
	// attr.nodeValue = in_value;
	// in_dst_element.setAttributeNode(attr);
// }

// �ʏ�\���C�x���g����
function test_onRestore() {
    document.getElementById("main_iframe").src = "restore.html";
	style = "normal";
}

// �ő�\���C�x���g����
function test_onMaximize() {
	document.getElementById("main_iframe").src = "maximize.html";
	style = "maxim";
}

// ���荞�݃C�x���g����
function test_onRestart() {
    location.reload();
}

// �C�x���g�n���h���o�^
widget.onrestore = test_onRestore;
widget.onmaximize = test_onMaximize;
widget.onrestart = test_onRestart;


//XMLHttpRequest�I�u�W�F�N�g����
function createHttpRequest(){

//Win ie�p
if(window.ActiveXObject){
	try {
		//MSXML2�ȍ~�p
		return new ActiveXObject("Msxml2.XMLHTTP") //[1]'
	} catch (e) {
		try {
			//��MSXML�p
			return new ActiveXObject("Microsoft.XMLHTTP") //[1]'
		} catch (e2) {
			return null
		}
	 }
} else if(window.XMLHttpRequest){
	//Win ie�ȊO��XMLHttpRequest�I�u�W�F�N�g�����u���E�U�p
   document.getElementById("debug").innerHTML = "XMLHttpRequest"
	return new XMLHttpRequest() //[1]'
} else {
   document.getElementById("debug").innerHTML = "null"
	return null
}
}

//�t�@�C���ɃA�N�Z�X����M���e���m�F���܂�
function requestFile( data , method , fileName , async )
{
	//XMLHttpRequest�I�u�W�F�N�g����
	var httpoj = createHttpRequest(); //[1]

	//URL���e�L�X�g�{�b�N�X����擾
	//fileName = document.getElementById("url").value
    //fileName = "atom.xml";
	fileName = "http://kivajapan.jp/?page=Atom";
	//fileName = "https://mail.google.com/mail/feed/atom/?atom_browser_auth"
	//fileName = "https://secure.softbank.ne.jp/yuji5296:yuji727@mail.google.com/mail/feed/atom/"

	//open ���\�b�h
	httpoj.open( method , fileName , async ); //[2]
	document.getElementById("debug").innerHTML = "open";

	//��M���ɋN������C�x���g
	httpoj.onreadystatechange = function()  //[4]
	{ 
	  //readyState�l��4�Ŏ�M����
	  if (httpoj.readyState==4)  //[5]
	  { 
		//�R�[���o�b�N
		on_loaded(httpoj)
	  }
	}
	document.getElementById("debug").innerHTML = httpoj.readyState

	//send ���\�b�h
	httpoj.send( data ) //[3]
}

function show_detail(id){
	document.getElementById('div_'+id).style.visibility = "visible";
	document.getElementById('list').style.visibility = "hidden";
	//document.getElementById('output').style.visibility = "hidden";
	//alert(id);
}
//ID�Ŏw�肳�ꂽ1���̏ڍ׏���\������
function show_detail2(id)
{
		
	//�G���g���[���̎擾
	number = xml.getElementsByTagName("entry").length;
	document.getElementById("debug").innerHTML = number ;

	//�^�C�g�����擾
	text = xml.getElementsByTagName('title')[0].firstChild.nodeValue;
	
	//�f�[�^�擾
	list = "<ul>";
	detail = "";//"<div style='position:absolute;top:0px;width:100%;'>";
	for (i = 0; i < number; i++) {
		item  = xml.getElementsByTagName("entry")[i];
		//document.getElementById("output").innerHTML = item ;
		if (id == item.getElementsByTagName('id')[0].firstChild.nodeValue){ 
			title = item.getElementsByTagName('title')[0].firstChild.nodeValue; 
			href = item.getElementsByTagName('link')[0].getAttribute("href"); 
			content	= item.getElementsByTagName('content')[0].firstChild.nodeValue; //[6]
			//�ڍו\��
			detail = detail + "<div id='" + href + "' onclick='show_detail3(id)' style='position:absolute;top:0px;visibility:visible;'><b>" + title + "</b>" + "<p>" + content + "</p></div>" ;
		}	
    }
	
	//�ڍ׏���\��
	document.getElementById("detail").innerHTML = detail ;
	document.getElementById('list').style.visibility = "hidden";
	//document.getElementById('output').style.visibility = "hidden";
	document.getElementById('detail').style.visibility = "visible";
	
}
//�ڍ׏����l�C�e�B�u�u���E�U�ŕ\������
//id�ɂ�URL���w��
function show_detail3(id){
	widget.openURL(id);
}
//�ʏ��ʂōŐV��entry��title��\������
function show_newest()
{
	//�G���g���[���̎擾
	number = xml.getElementsByTagName("entry").length;
	
	//�P���\�����I�������ŏ��ɖ߂�
	if ( counter >= number ) counter = 0;
	
	item  = xml.getElementsByTagName("entry")[counter];
	title = item.getElementsByTagName('title')[0].firstChild.nodeValue; 
	list = "<div>" + title + "</div>";

	//�\��
	document.getElementById("newest").innerHTML = list ;
	
	//�^�C�}�[�Z�b�g
	timerID = setTimeout("show_newest()", 2000);
	
	//���̃A�C�e���̕\������
	counter = counter + 1;
}

//�ꗗ��\������
function show_list()
{

	//�G���g���[���̎擾
	number = xml.getElementsByTagName("entry").length;
	document.getElementById("debug").innerHTML = number ;

	//�^�C�g�����擾
	maintitle = xml.getElementsByTagName('title')[0].firstChild.nodeValue;
	subtitle = xml.getElementsByTagName('subtitle')[0].firstChild.nodeValue ;
	updated =  xml.getElementsByTagName('updated')[0].firstChild.nodeValue ;
	list = maintitle + "[" + updated + "]" + "<marquee>" + subtitle + "</marquee>";
	
	//�f�[�^�擾
	list = list + "<ul>";
	//detail = "";//"<div style='position:absolute;top:0px;width:100%;'>";
	for (i = 0; i < number; i++) {
		item  = xml.getElementsByTagName("entry")[i];
		//document.getElementById("output").innerHTML = item ;
		title = item.getElementsByTagName('title')[0].firstChild.nodeValue; 
		id = item.getElementsByTagName('id')[0].firstChild.nodeValue; 
		//href = item.getElementsByTagName('link')[0].getAttribute("href"); 
		//content	= item.getElementsByTagName('content')[0].firstChild.nodeValue; //[6]
		//�ꗗ�\��
		list = list + "<li><a id=" + id + " onclick='show_detail2(id);'>" + title + "</a></li>";
		//�ڍו\��
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

//�R�[���o�b�N�֐� ( ��M���Ɏ��s����܂� )
function on_loaded(oj)
{
	//���X�|���X���擾
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

//�R���e���c�ؑ֗p�C�x���g�n���h��
function help(){
	document.getElementById("body").src = "help.html";
}
function rsslist(){
	document.getElementById("body").src = "rsslist.html";
}
