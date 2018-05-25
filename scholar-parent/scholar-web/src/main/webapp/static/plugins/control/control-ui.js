$(function() {
	$.fn.ComboBox = function(options) {
		//options参数：description,height,width,allowSearch,url,param,data
		var $select = $(this);
		if(!$select.attr('id')) {
			return false;
		}
		if(options) {
			if($select.find('.ui-select-text').length == 0) {
				var $select_html = "";
				$select_html += "<div class=\"ui-select-text\" style='color:#999;'>" + options.description + "</div>";
				$select_html += "<div class=\"ui-select-option\">";
				$select_html += "<div class=\"ui-select-option-content\" style=\"max-height: " + options.height + "\">" + $select.html() + "</div>";
				if(options.allowSearch) {
					$select_html += "<div class=\"ui-select-option-search\"><input type=\"text\" class=\"form-control\" placeholder=\"搜索关键字\" /><span class=\"input-query\" title=\"Search\"><i class=\"fa fa-search\"></i></span></div>";
				}
				$select_html += "</div>";
				$select.html('');
				$select.append($select_html);
			}
		}
		var $option_html = $($("<p>").append($select.find('.ui-select-option').clone()).html());
		$option_html.attr('id', $select.attr('id') + '-option');
		$select.find('.ui-select-option').remove();
		if($option_html.length > 0) {
			$('body').find('#' + $select.attr('id') + '-option').remove();
		}
		$('body').prepend($option_html);
		var $option = $("#" + $select.attr('id') + "-option");
		if(options.url != undefined) {
			$option.find('.ui-select-option-content').html('');
			$.ajax({
				url: options.url,
				data: options.param,
				type: "GET",
				dataType: "json",
				async: false,
				success: function(data) {
					options.data = data;
					var json = data;
					loadComboBoxView(json);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					dialogMsg(errorThrown, -1);
				}
			});
		} else if(options.data != undefined) {
			var json = options.data;
			loadComboBoxView(json);
		} else {
			$option.find('li').css('padding', "0 5px");
			$option.find('li').click(function(e) {
				var data_text = $(this).text();
				var data_value = $(this).attr('data-value');
				$select.attr("data-value", data_value).attr("data-text", data_text);
				$select.find('.ui-select-text').html(data_text).css('color', '#000');
				$option.slideUp(150);
				$select.trigger("change");
				e.stopPropagation();
			}).hover(function(e) {
				if(!$(this).hasClass('liactive')) {
					$(this).toggleClass('on');
				}
				e.stopPropagation();
			});
		}

		function loadComboBoxView(json, searchValue, m) {
			if(json.length > 0) {
				var $_html = $('<ul></ul>');
				if(options.description) {
					$_html.append('<li data-value="">' + options.description + '</li>');
				}
				$.each(json, function(i) {
					var row = json[i];
					var title = row[options.title];
					if(title == undefined) {
						title = "";
					}
					if(searchValue != undefined) {
						if(row[m.text].indexOf(searchValue) != -1) {
							$_html.append('<li data-value="' + row[options.id] + '" title="' + title + '">' + row[options.text] + '</li>');
						}
					} else {
						$_html.append('<li data-value="' + row[options.id] + '" title="' + title + '">' + row[options.text] + '</li>');
					}
				});
				$option.find('.ui-select-option-content').html($_html);
				$option.find('li').css('padding', "0 5px");
				$option.find('li').click(function(e) {
					var data_text = $(this).text();
					var data_value = $(this).attr('data-value');
					$select.attr("data-value", data_value).attr("data-text", data_text);
					$select.find('.ui-select-text').html(data_text).css('color', '#000');
					$option.slideUp(150);
					$select.trigger("change");
					e.stopPropagation();
				}).hover(function(e) {
					if(!$(this).hasClass('liactive')) {
						$(this).toggleClass('on');
					}
					e.stopPropagation();
				});
			}
		}
		//操作搜索事件
		if(options.allowSearch) {
			$option.find('.ui-select-option-search').find('input').bind("keypress", function(e) {
				if(event.keyCode == "13") {
					var value = $(this).val();
					loadComboBoxView($(this)[0].options.data, value, $(this)[0].options);
				}
			}).focus(function() {
				$(this).select();
			})[0]["options"] = options;
		}

		$select.unbind('click');
		$select.bind("click", function(e) {
			if($select.attr('readonly') == 'readonly' || $select.attr('disabled') == 'disabled') {
				return false;
			}
			$(this).addClass('ui-select-focus');
			if($option.is(":hidden")) {
				$select.find('.ui-select-option').hide();
				$('.ui-select-option').hide();
				var left = $select.offset().left;
				var top = $select.offset().top + 29;
				var width = $select.width();
				if(options.width) {
					width = options.width;
				}
				if(($option.height() + top) < $(window).height()) {
					$option.slideDown(150).css({
						top: top,
						left: left,
						width: width
					});
				} else {
					var _top = (top - $option.height() - 32)
					$option.show().css({
						top: _top,
						left: left,
						width: width
					});
					$option.attr('data-show', true);
				}
				$option.css('border-top', '1px solid #ccc');
				$option.find('li').removeClass('liactive');
				$option.find('[data-value=' + $select.attr('data-value') + ']').addClass('liactive');
				$option.find('.ui-select-option-search').find('input').select();
			} else {
				if($option.attr('data-show')) {
					$option.hide();
				} else {
					$option.slideUp(150);
				}
			}
			e.stopPropagation();
		});
		$(document).click(function(e) {
			var e = e ? e : window.event;
			var tar = e.srcElement || e.target;
			if(!$(tar).hasClass('form-control')) {
				if($option.attr('data-show')) {
					$option.hide();
				} else {
					$option.slideUp(150);
				}
				$select.removeClass('ui-select-focus');
				e.stopPropagation();
			}
		});
		return $select;
	}
	$.fn.ComboBoxSetValue = function(value) {
		if($.isNullOrEmpty(value)) {
			return;
		}
		var $select = $(this);
		var $option = $("#" + $select.attr('id') + "-option");
		$select.attr('data-value', value);
		var data_text = $option.find('ul').find('[data-value=' + value + ']').html();
		if(data_text) {
			$select.attr('data-text', data_text);
			$select.find('.ui-select-text').html(data_text).css('color', '#000');
			$option.find('ul').find('[data-value=' + value + ']').addClass('liactive')
		}
		return $select;
	}
	$.fn.ComboBoxTree = function(options) {
		//options参数：description,height,allowSearch,appendTo,click,url,param,method,icon
		var $select = $(this);
		if(!$select.attr('id')) {
			return false;
		}
		if($select.find('.ui-select-text').length == 0) {
			var $select_html = "";
			$select_html += "<div class=\"ui-select-text\"  style='color:#999;'>" + options.description + "</div>";
			$select_html += "<div class=\"ui-select-option\">";
			$select_html += "<div class=\"ui-select-option-content\" style=\"max-height: " + options.height + "\"></div>";
			if(options.allowSearch) {
				$select_html += "<div class=\"ui-select-option-search\"><input type=\"text\" class=\"form-control\" placeholder=\"搜索关键字\" /><span class=\"input-query\" title=\"Search\"><i class=\"fa fa-search\" title=\"按回车查询\"></i></span></div>";
			}
			$select_html += "</div>";
			$select.append($select_html);
		}

		var $option_html = $($("<p>").append($select.find('.ui-select-option').clone()).html());
		$option_html.attr('id', $select.attr('id') + '-option');
		$select.find('.ui-select-option').remove();
		if(options.appendTo) {
			$(options.appendTo).prepend($option_html);
		} else {
			$('body').prepend($option_html);
		}
		var $option = $("#" + $select.attr('id') + "-option");
		var $option_content = $("#" + $select.attr('id') + "-option").find('.ui-select-option-content');
		loadtreeview(options.url);

		function loadtreeview(url) {
			$option_content.treeview({
				onnodeclick: function(item) {
					$select.attr("data-value", item.id).attr("data-text", item.text);
					$select.find('.ui-select-text').html(item.text).css('color', '#000');
					$select.trigger("change");
					if(options.click) {
						options.click(item);
					}
				},
				height: options.height,
				url: url,
				param: options.param,
				method: options.method,
				data: options.data,
				showcheck: options.showcheck,
				description: options.description
			});
		}
		if(options.allowSearch) {
			$option.find('.ui-select-option-search').find('input').attr('data-url', options.url);
			$option.find('.ui-select-option-search').find('input').bind("keypress", function(e) {
				if(event.keyCode == "13") {
					var value = $(this).val();
					var url = changeUrlParam($option.find('.ui-select-option-search').find('input').attr('data-url'), options.keyword ? options.keyword : "keyword", value);
					loadtreeview(url);
				}
			}).focus(function() {
				$(this).select();
			});
		}
		if(options.icon) {
			$option.find('i').remove();
			$option.find('img').remove();
		}
		$select.find('.ui-select-text').unbind('click');
		$select.find('.ui-select-text').bind("click", function(e) {
			if($select.attr('readonly') == 'readonly' || $select.attr('disabled') == 'disabled') {
				return false;
			}
			$(this).parent().addClass('ui-select-focus');
			if($option.is(":hidden")) {
				$select.find('.ui-select-option').hide();
				$('.ui-select-option').hide();
				var left = $select.offset().left;
				var top = $select.offset().top + 29;
				var width = $select.width();
				if(options.width) {
					width = options.width;
				}
				if(($option.height() + top) < $(window).height()) {
					$option.slideDown(150).css({
						top: top,
						left: left,
						width: width
					});
				} else {
					var _top = (top - $option.height() - 32);
					$option.show().css({
						top: _top,
						left: left,
						width: width
					});
					$option.attr('data-show', true);
				}
				$option.css('border-top', '1px solid #ccc');
				if(options.appendTo) {
					$option.css("position", "inherit")
				}
				$option.find('.ui-select-option-search').find('input').select();
			} else {
				if($option.attr('data-show')) {
					$option.hide();
				} else {
					$option.slideUp(150);
				}
			}
			e.stopPropagation();
		});
		$select.find('li div').click(function(e) {
			var e = e ? e : window.event;
			var tar = e.srcElement || e.target;
			if(!$(tar).hasClass('bbit-tree-ec-icon')) {
				$option.slideUp(150);
				e.stopPropagation();
			}
		});
		$(document).click(function(e) {
			var e = e ? e : window.event;
			var tar = e.srcElement || e.target;
			if(!$(tar).hasClass('bbit-tree-ec-icon') && !$(tar).hasClass('form-control')) {
				if($option.attr('data-show')) {
					$option.hide();
				} else {
					$option.slideUp(150);
				}
				$select.removeClass('ui-select-focus');
				e.stopPropagation();
			}
		});
		return $select;
	}
	$.fn.ComboBoxTreeSetValue = function(value) {
		if(value == "") {
			return;
		}
		var $select = $(this);
		var $option = $("#" + $select.attr('id') + "-option");
		$select.attr('data-value', value);
		var data_text = $option.find('ul').find('[data-value=' + value + ']').html();
		if(data_text) {
			$select.attr('data-text', data_text);
			$select.find('.ui-select-text').html(data_text).css('color', '#000');
			$option.find('ul').find('[data-value=' + value + ']').parent().parent().addClass('bbit-tree-selected');
		}
		return $select;
	}
	$.fn.GetWebControls = function(keyValue) {
		var reVal = "";
		$(this).find('input,select,textarea,.ui-select').each(function(r) {
			var id = $(this).attr('id');
			var type = $(this).attr('type');
			switch(type) {
				case "checkbox":
					if($("#" + id).is(":checked")) {
						reVal += '"' + id + '"' + ':' + '"1",'
					} else {
						reVal += '"' + id + '"' + ':' + '"0",'
					}
					break;
				case "select":
					var value = $("#" + id).attr('data-value');
					if(value == "") {
						value = "&nbsp;";
					}
					reVal += '"' + id + '"' + ':' + '"' + $.trim(value) + '",'
					break;
				case "selectTree":
					var value = $("#" + id).attr('data-value');
					if(value == "") {
						value = "&nbsp;";
					}
					reVal += '"' + id + '"' + ':' + '"' + $.trim(value) + '",'
					break;
				default:
					var value = $("#" + id).val();
					if(value == "") {
						value = "&nbsp;";
					}
					reVal += '"' + id + '"' + ':' + '"' + $.trim(value) + '",'
					break;
			}
		});
		reVal = reVal.substr(0, reVal.length - 1);
		if(!keyValue) {
			reVal = reVal.replace(/&nbsp;/g, '');
		}
		reVal = reVal.replace(/\\/g, '\\\\');
		reVal = reVal.replace(/\n/g, '\\n');
		var postdata = jQuery.parseJSON('{' + reVal + '}');
		//阻止伪造请求
		//if ($('[name=__RequestVerificationToken]').length > 0) {
		//    postdata["__RequestVerificationToken"] = $('[name=__RequestVerificationToken]').val();
		//}
		return postdata;
	};
	$.fn.SetWebControls = function(data) {
		var $id = $(this)
		for(var key in data) {
			var id = $id.find('#' + key);
			if(id.attr('id')) {
				var type = id.attr('type');
				if(id.hasClass("input-datepicker")) {
					type = "datepicker";
				}
				var value = $.trim(data[key]).replace(/&nbsp;/g, '');
				switch(type) {
					case "checkbox":
						if(value == 1) {
							id.attr("checked", 'checked');
						} else {
							id.removeAttr("checked");
						}
						break;
					case "select":
						id.ComboBoxSetValue(value);
						break;
					case "selectTree":
						id.ComboBoxTreeSetValue(value);
						break;
					case "datepicker":
						id.val(formatDate(value, 'yyyy-MM-dd'));
						break;
					default:
						id.val(value);
						break;
				}
			}
		}
	}
})
reload = function() {
	location.reload();
	return false;
}
guid = function() {
	var guid = "";
	for(var i = 1; i <= 36; i++) {
		var n = Math.floor(Math.random() * 16.0).toString(16);
		guid += n;
//		if((i == 8) || (i == 12) || (i == 16) || (i == 20)) guid += "-";
	}
	return guid;
}
formatDate = function(v, format) {
	if(!v) return "";
	var d = v;
	if(typeof v === 'string') {
		if(v.indexOf("/Date(") > -1)
			d = new Date(parseInt(v.replace("/Date(", "").replace(")/", ""), 10));
		else
			d = new Date(Date.parse(v.replace(/-/g, "/").replace("T", " ").split(".")[0])); //.split(".")[0] 用来处理出现毫秒的情况，截取掉.xxx，否则会出错
	}
	var o = {
		"M+": d.getMonth() + 1, //month
		"d+": d.getDate(), //day
		"h+": d.getHours(), //hour
		"m+": d.getMinutes(), //minute
		"s+": d.getSeconds(), //second
		"q+": Math.floor((d.getMonth() + 3) / 3), //quarter
		"S": d.getMilliseconds() //millisecond
	};
	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for(var k in o) {
		if(new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}; toDecimal = function(num) {
	if(num == null) {
		num = "0";
	}
	num = num.toString().replace(/\$|\,/g, '');
	if(isNaN(num))
		num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num * 100 + 0.50000000001);
	cents = num % 100;
	num = Math.floor(num / 100).toString();
	if(cents < 10)
		cents = "0" + cents;
	for(var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		num = num.substring(0, num.length - (4 * i + 3)) + '' +
		num.substring(num.length - (4 * i + 3));
	return(((sign) ? '' : '-') + num + '.' + cents);
}
Date.prototype.DateAdd = function(strInterval, Number) {
	//y年 q季度 m月 d日 w周 h小时 n分钟 s秒 ms毫秒
	var dtTmp = this;
	switch(strInterval) {
		case 's':
			return new Date(Date.parse(dtTmp) + (1000 * Number));
		case 'n':
			return new Date(Date.parse(dtTmp) + (60000 * Number));
		case 'h':
			return new Date(Date.parse(dtTmp) + (3600000 * Number));
		case 'd':
			return new Date(Date.parse(dtTmp) + (86400000 * Number));
		case 'w':
			return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
		case 'q':
			return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number * 3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
		case 'm':
			return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
		case 'y':
			return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
	}
}
request = function(keyValue) {
	var search = location.search.slice(1);
	var arr = search.split("&");
	for(var i = 0; i < arr.length; i++) {
		var ar = arr[i].split("=");
		if(ar[0] == keyValue) {
			if(unescape(ar[1]) == 'undefined') {
				return "";
			} else {
				return unescape(ar[1]);
			}
		}
	}
	return "";
}
changeUrlParam = function(url, key, value) {
	var newUrl = "";
	var reg = new RegExp("(^|)" + key + "=([^&]*)(|$)");
	var tmp = key + "=" + value;
	if(url.match(reg) != null) {
		newUrl = url.replace(eval(reg), tmp);
	} else {
		if(url.match("[\?]")) {
			newUrl = url + "&" + tmp;
		} else {
			newUrl = url + "?" + tmp;
		}
	}
	return newUrl;
}

$.isbrowsername = function() {
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
	var isOpera = userAgent.indexOf("Opera") > -1;
	if(isOpera) {
		return "Opera"
	}; //判断是否Opera浏览器
	if(userAgent.indexOf("Firefox") > -1) {
		return "FF";
	} //判断是否Firefox浏览器
	if(userAgent.indexOf("Chrome") > -1) {
		if(window.navigator.webkitPersistentStorage.toString().indexOf('DeprecatedStorageQuota') > -1) {
			return "Chrome";
		} else {
			return "360";
		}
	} //判断是否Chrome浏览器//360浏览器
	if(userAgent.indexOf("Safari") > -1) {
		return "Safari";
	} //判断是否Safari浏览器
	if(userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
		return "IE";
	}; //判断是否IE浏览器
}
$.download = function(url, data, method) {
	if(url && data) {
		data = typeof data == 'string' ? data : jQuery.param(data);
		var inputs = '';
		$.each(data.split('&'), function() {
			var pair = this.split('=');
			inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
		});
		$('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>').appendTo('body').submit().remove();
	};
};

$.isNullOrEmpty = function(obj) {
	if((typeof(obj) == "string" && obj == "") || obj == null || obj == undefined) {
		return true;
	} else {
		return false;
	}
}
$.arrayClone = function(data) {
	return $.map(data, function(obj) {
		return $.extend(true, {}, obj);
	});
}
$.windowWidth = function() {
	return $(window).width();
}
$.windowHeight = function() {
	return $(window).height();
}
IsNumber = function(obj) {
	$("#" + obj).bind("contextmenu", function() {
		return false;
	});
	$("#" + obj).css('ime-mode', 'disabled');
	$("#" + obj).keypress(function(e) {
		if(e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			return false;
		}
	});
}
IsMoney = function(obj) {
	$("#" + obj).bind("contextmenu", function() {
		return false;
	});
	$("#" + obj).css('ime-mode', 'disabled');
	$("#" + obj).bind("keydown", function(e) {
		var key = window.event ? e.keyCode : e.which;
		if(isFullStop(key)) {
			return $(this).val().indexOf('.') < 0;
		}
		return(isSpecialKey(key)) || ((isNumber(key) && !e.shiftKey));
	});

	function isNumber(key) {
		return key >= 48 && key <= 57
	}

	function isSpecialKey(key) {
		return key == 8 || key == 46 || (key >= 37 && key <= 40) || key == 35 || key == 36 || key == 9 || key == 13
	}

	function isFullStop(key) {
		return key == 190 || key == 110;
	}
}
checkedArray = function(id) {
	var isOK = true;
	if(id == undefined || id == "" || id == 'null' || id == 'undefined') {
		isOK = false;
		dialogMsg('您没有选中任何项,请您选中后再操作。', 0);
	}
	return isOK;
}
checkedRow = function(id) {
	var isOK = true;
	if(id == undefined || id == "" || id == 'null' || id == 'undefined') {
		isOK = false;
		dialogMsg('您没有选中任何数据项,请选中后再操作！', 0);
	} else if(id.split(",").length > 1) {
		isOK = false;
		dialogMsg('很抱歉,一次只能选择一条记录！', 0);
	}
	return isOK;
}