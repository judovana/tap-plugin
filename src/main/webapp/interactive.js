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