alert(2)
app.controller('bottomMenu', function($scope,$window,$rootScope,$http,$location) {
$rootScope.showSearchInput=false;
$rootScope.showSearch = false;
$rootScope.isLogin = false;
$rootScope.selectedItem=null
$rootScope.polName={"CNAQG":"ANQING","THBKK":"BANGKOK","IDBLW":"BELAWAN","KRPUS":"BUSAN","VNCMT":"CAI MEP","INMAA":"CHENNAI","INCOK":"COCHIN","LKCMB":"COLOMBO","CNDCB":"DA CHAN BAY","CNDLC":"DALIAN","PHDCT":"DAVAO, MINDANAO","CNDGG":"DONGGUAN","JPFKY":"FUKUYAMA","PHGES":"GENERAL SANTOS","VNHPH":"HAIPHONG","INHZR":"HAZIRA PORT","JPHIJ":"HIROSHIMA","VNSGN":"HO CHI MINH CITY","HKHKG":"HONG KONG","JPHSM":"HOSOSHIMA","CNHUA":"HUANGPU","JPIMI":"IMARI","KRICH":"INCHEON","IDJKT":"JAKARTA","AEDXB":"JEBEL ALI, U.A.E","CNJMN":"JIANGMEN","JPKNZ":"KANAZAWA","PKKHI":"KARACHI","INKAT":"KATTUPALLI","TWKEL":"KEELUNG","AEKLF":"KHOR AL FAKKAN","INCCU":"KOLKATA (EX CALCUTTA)","INKRI":"KRISHNAPATNAM","THLCH":"LAEM CHABANG","JPMAI":"MAIZURU","MVMAL":"MALE","PHMNL":"MANILA","JPMKX":"MISHIMA KAWANOE","JPMIZ":"MIZUSHIMA","INMUN":"MUNDRA","CNNAH":"NANHAI","CNNSA":"NANSHA","INNSA":"NHAVA SHEVA","JPKIJ":"NIIGATA","CNNGB":"NINGBO","JPOIT":"OITA","JPOTR":"OTARU","IDPLM":"PALEMBANG","IDPNJ":"PANJANG","INPPT":"PARADIP","MYPGU":"PASIR GUDANG  JOHOR","MYPEN":"PENANG","KHPNH":"PHNOM PENH PORT","INPAV":"PIPAVAV PORT","MYPKG":"PORT KLANG","PKQCT":"QASIM","CNTAO":"QINGDAO","CNQZH":"QINZHOU","JPSMN":"SAKAIMINATO","CNSHA":"SHANGHAI","CNSWA":"SHANTOU","CNSHK":"SHEKOU","JPSBS":"SHIBUSHI","CNSUD":"SHUNDE","KHKOS":"SIHANOUKVILLE","SGSIN":"SINGAPORE","OMSOH":"SOHAR","THSGZ":"SONGKHLA","PHSFS":"SUBIC BAY","IDSUB":"SURABAYA","TWTXG":"TAICHUNG","CNTXG":"TIANJIN XINGANG","JPTOY":"TOYAMA","INTUT":"TUTICORIN","INVTZ":"VISAKHAPATNAM","CNWNZ":"WENZHOU","CNWUH":"WUHAN","CNWHI":"WUHU","CNXMN":"XIAMEN","MMRGN":"YANGON","CNYTN":"YANTIAN","CNZHA":"ZHANJIANG","CNZSN":"ZHONGSHAN"};

$rootScope.countryName={};

$scope.getCountryAjax= function(){

		$http({
				method : "GET",
				async : true,
				url : "https://eservice.rclgroup.com/DCSWebService/rclws/commonService/getCountryByCode",
				headers: {'Content-Type': undefined},
						processData : false,
						data : {},
			  }).then(function(result, status, headers, config) {	
					$rootScope.countryName=result;
					console.log(result);
							 alert(result);
							
			});
	
}

//$scope.getCountryAjax();

$scope.showSearchFun = function ( ) {

  $rootScope.showSearchInput = !$rootScope.showSearchInput;
};

$scope.go = function ( path ) {

  $location.path( path );
};

$scope.showpages= function(){

	if($rootScope.isLogin){
			$location.path("/dashBord");
			//alert($rootScope.isLogin);
	}else{
	$scope.go("/");
	}
}

$scope.logout= function()
{
	$scope.go("/");
	$rootScope.showSearchInput=false;
	$rootScope.showSearch = false;
	$rootScope.isLogin = false;
	$rootScope.selectedItem=null
}


});
