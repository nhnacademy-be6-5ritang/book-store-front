var categoryCount = /*[[${bookCategories.size()}]]*/ 0; // 초기 카테고리 개수 설정

function addCategorySelect() {
    categoryCount++;

    var categorySelectRow = document.createElement('div');
    categorySelectRow.classList.add('category-select-row');

    var label = document.createElement('label');
    label.setAttribute('for', 'categories');
    label.classList.add('mr-2');
    label.textContent = '카테고리 ' + categoryCount;

    var flexContainer = document.createElement('div');
    flexContainer.classList.add('d-flex', 'align-items-center');

    var select = document.createElement('select');
    select.classList.add('form-control');
    select.setAttribute('id', 'categories');
    select.setAttribute('name', 'categories');
    select.setAttribute('required', '');

    var defaultOption = document.createElement('option');
    defaultOption.setAttribute('value', '');
    defaultOption.textContent = '카테고리 선택';
    select.appendChild(defaultOption);

    /* 카테고리 옵션들 추가 */
    var categories = /*[[${categories}]]*/ [];
    categories.forEach(function (category) {
        var option = document.createElement('option');
        option.setAttribute('value', category.categoryId);
        option.textContent = category.categoryName;
        select.appendChild(option);
    });

    var deleteButton = document.createElement('button');
    deleteButton.setAttribute('type', 'button');
    deleteButton.classList.add('btn', 'btn-danger', 'ml-2', 'inline-text');
    deleteButton.textContent = '삭제';
    deleteButton.onclick = function () {
        removeCategorySelect(deleteButton);
    };

    flexContainer.appendChild(select);
    flexContainer.appendChild(deleteButton);

    categorySelectRow.appendChild(label);
    categorySelectRow.appendChild(flexContainer);

    document.getElementById('categorySelectionGroup').appendChild(categorySelectRow);
}

function removeCategorySelect(buttonElement) {
    var selectRow = buttonElement.closest('.category-select-row');

    // 남은 카테고리 선택 항목의 개수 확인
    var remainingCategorySelects = document.querySelectorAll('.category-select-row');

    // 첫 번째 항목은 삭제할 수 없도록 체크
    if (remainingCategorySelects.length > 1) {
        selectRow.remove();
        categoryCount--;
    } else {
        alert("하나의 카테고리는 삭제할 수 없습니다.");
    }
}

var tagCount = /*[[${bookTags.size()}]]*/ 0; // 초기 태그 개수 설정

function addTagSelect() {
    tagCount++;

    var tagSelectRow = document.createElement('div');
    tagSelectRow.classList.add('tag-select-row');

    var label = document.createElement('label');
    label.setAttribute('for', 'tags');
    label.classList.add('mr-2');
    label.textContent = '태그 ' + tagCount;

    var flexContainer = document.createElement('div');
    flexContainer.classList.add('d-flex', 'align-items-center');

    var select = document.createElement('select');
    select.classList.add('form-control');
    select.setAttribute('id', 'tags');
    select.setAttribute('name', 'tags');
    select.setAttribute('required', '');

    var defaultOption = document.createElement('option');
    defaultOption.setAttribute('value', '');
    defaultOption.textContent = '태그 선택';
    select.appendChild(defaultOption);

    /* 태그 옵션들 추가 */
    var tags = /*[[${tags}]]*/ [];
    tags.forEach(function (tag) {
        var option = document.createElement('option');
        option.setAttribute('value', tag.tagId);
        option.textContent = tag.tagName;
        select.appendChild(option);
    });

    var deleteButton = document.createElement('button');
    deleteButton.setAttribute('type', 'button');
    deleteButton.classList.add('btn', 'btn-danger', 'ml-2', 'inline-text');
    deleteButton.textContent = '삭제';
    deleteButton.onclick = function () {
        removeTagSelect(deleteButton);
    };

    flexContainer.appendChild(select);
    flexContainer.appendChild(deleteButton);

    tagSelectRow.appendChild(label);
    tagSelectRow.appendChild(flexContainer);

    document.getElementById('tagSelectionGroup').appendChild(tagSelectRow);
}

function removeTagSelect(buttonElement) {
    var selectRow = buttonElement.closest('.tag-select-row');
    selectRow.remove();

    var label = selectRow.querySelector('label');
    label.remove();

    tagCount--;
}
