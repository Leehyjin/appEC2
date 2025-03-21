// Vue 객체를 사용하여 화면결과 UI Binding
const searchResult = new Vue({
    el: '#search-result',
    data: {
        search_result: {},
    },
});

// 검색결과 리스트 UI용 Vue 객체
const resultList = new Vue({
    el: '#wish-list-result',
    data: {
        result_list: {},
    },
    methods: {
        deleteResult(resultId) {
            console.log('resultlist delete resultid', resultId);

            $.ajax({
                type: 'post',          // http method를 post로 요청
                url: '/api/resultdelete/' + resultId,  // api url
                contentType: 'application/json',
                success: function(response, status, xhr) {
                    console.log('검색결과 삭제 완료', response);

                    // 다시 검색결과 목록 조회
                    $.get('/api/resultall', function(response) {
                        resultList.result_list = response;
                    });
                },
                error: function(request, status, error) {
                    alert('검색리스트 결과 삭제에 실패하였습니다.')
                },
            });
        },
        addVisitCount(resultId) {
            console.log('resultlist addVisitCount resultid', resultId);

            $.ajax({
                type: 'post',          // http method를 post로 요청
                url: '/api/resultvisit/' + resultId,  // api url
                contentType: 'application/json',
                success: function(response, status, xhr) {
                    console.log('방문 카운트 증가 완료', response);

                    // 다시 검색결과 목록 조회
                    $.get('/api/resultall', function(response) {
                        resultList.result_list = response;
                    });
                },
                error: function(request, status, error) {
                    alert('검색리스트 방문 카운트 추가에 실패하였습니다.')
                },
            });
        },
    }
});

// jquery로 처음 페이지 로딩될 시에 호출되는 메소드
$(document).ready(function() {
    console.log('jquery ready');

    $('#search-result').hide(); // 처음 로딩시 검색결과를 숨김
    $('#wish-list-result').hide();// 처음 로딩시 검색결과저장 정보를 숨김

    // 검색결과저장 목록 가져오기
    $.get('/api/resultall', function(response) {
        resultList.result_list = response;

        $('#wish-list-result').show();
    });
});

// 검색란에서 검색어를 입력하고 검색 버튼을 눌렀을 때
$('#searchButton').click(function() {
    console.log('search btn click');

    // 검색란에서 검색어 값 가져오기
    const query = $('#searchBox').val();

    // 실제 backend에 /api/search url를 요청
    $.get('/api/search?searchQuery=' + query, function(response) {
        console.log('search response값', response);

        searchResult.search_result = response;

        const title = document.getElementById('wish-title');
        if (title) {
            title.innerHTML = searchResult.search_result.title.replace(/<^>]*>?/g, '');
        }

        $('#search-result').show();
    });
});

// 검색창에서 enter키를 눌렀을 시 이벤트 처리
$('#searchBox').on('keyup', function(event) {
    console.log('key1');
    if(event.keyCode == 13) {       // 13번은 Enter키
        console.log('enter key press');

        $('#searchButton').click(); // 실제 버튼 클릭 이벤트 실행
    }
});

// 검색리스트 추가 버튼 클릭시
$('#wish-button').click(function () {
    console.log('wish btn click');

    // jquery ajax 비동기로 검색리스트 내용을 post로 추가요청
    $.ajax({
        type: 'post',          // http method를 post로 요청
        url: '/api/resultadd',  // api url
        contentType: 'application/json',
        data: JSON.stringify(searchResult.search_result),   // post로 보낼 파라미터(json으로 보냄)
        success: function(response, status, xhr) {
            console.log('검색리스트 결과 추가 완료', response);

            // response데이터를 화면에 보이게 하기
            resultList.result_list = response;
        },
        error: function(request, status, error) {
            alert('검색리스트 결과 추가에 실패하였습니다.')
        },
    });
})
