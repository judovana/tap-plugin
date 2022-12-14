function showHideBlock(clazz) {
  showHide(clazz, 'block')
}

function showHideRow(clazz) {
  showHide(clazz, 'table-row')
}

function showHideRowGroup(clazz) {
  showHideRow("test_" + clazz)
  showHideRow("tr_details_" + clazz)
}

function showHideInline(clazz, type) {
  showHide(clazz, 'inline')
}

function showHide(clazz, type) {
  var all = document.getElementsByClassName(clazz);
  for (var i = 0; i < all.length; i ++) {
    if (all[i].style.display=='none') {all[i].style.display = type} else { all[i].style.display = 'none'};
  }
}

function showHideRowOne(id) {
  showHideOne(id, 'table-row')
}

function showHideInlineOne(id) {
  showHideOne(id, 'inline')
}

function showHideBlockOne(id) {
  showHideOne(id, 'block')
}

function showHideOne(id, type) {
  var el = document.getElementById(id);
  if (el.style.display=='none') {el.style.display = type} else {el.style.display = 'none'};
}

function set(clazz, type) {
  var all = document.getElementsByClassName(clazz);
  for (var i = 0; i < all.length; i ++) {
    all[i].style.display = type;
  }
}

function showFailed() {
  set("test_ok","none")
  set("test_not ok","table-row")
  set("test_ok_SKIP","none")
  set("tr_details_ok","none")
  set("tr_details_not ok","none")
  set("tr_details_ok_SKIP","none")
  set("detail_body","none")
}

function showFailedDetails() {
  set("test_ok","none")
  set("test_not ok","table-row")
  set("test_ok_SKIP","none")
  set("tr_details_ok","none")
  set("tr_details_not ok","table-row")
  set("tr_details_ok_SKIP","none")
  set("detail_body","block")
}

function hideDetails() {
  set("test_ok","none")
  set("test_not ok","table-row")
  set("test_ok_SKIP","none")
  set("tr_details_ok","none")
  set("tr_details_not ok","table-row")
  set("tr_details_ok_SKIP","table-none")
  set("detail_body","none")
}

function showNothing() {
  set("test_ok","none")
  set("test_not ok","none")
  set("test_ok_SKIP","none")
  set("tr_details_ok","none")
  set("tr_details_not ok","none")
  set("tr_details_ok_SKIP","none")
  set("detail_body","none")
}

function showAll(){
  set("test_ok","table-row")
  set("test_not ok","table-row")
  set("test_ok_SKIP","table-row")
  set("tr_details_ok","table-row")
  set("tr_details_not ok","table-row")
  set("tr_details_ok_SKIP","table-row")
  set("detail_body","block")
}