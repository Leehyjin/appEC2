const searchResult = new Vue({
    el: '#search-result',
    data: {
        search_result: {},
    }
});

const resultList = new Vue({
    el: '#wish-list-result',
    data: {
        result_list: {}
    },
    methods: {
        deleteResult(resultId) {
            console.log('resultlist delete resultid', resultId);

            $.ajax({
                type: 'post',
                url: '/api/resultdelte' + resultId,
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
                }
            });
        },
        addVisitCount(resultId) {
            console.log('resultList addVisitCount resultid', resultId);

            $.ajax({
                type: 'post',
                url: '/api/resultvisit/' + resultId,
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
                }
            });
        }
    }
});

// jQuery로 처음 페이지 로딩 될 시 호출되는 메서드
$(document).ready(function() {  // 수정: function({ -> function()
    console.log('jquery ready');

    $('#search-result').hide(); // 처음 로딩시 검색 결과 숨김
    $('#wish-list-result').hide(); // 처음 로딩 시 검색결과 저장 정보 숨김

    $.get('api/resultall', function(response) {
        resultList.result_list = response;
    });
});

// 검색란에서 검색어를 입력하고 검색버튼 눌렀을 때
$('#searchButton').click(function() {
    console.log('search btn click');

    // 검색란에서 검색어 값 가져오기
    const query = $('#searchBox').val();

    $.get('api/search?searchQuery=' + query, function(response) {  // 수정: 'funtion' -> 'function'
        console.log('search response값', response);

        searchResult.search_result = response;

        const title = document.getElementById('wish-title');
        if (title) {
            title.innerHTML = searchResult.search_result.replace(/<^>]*>?/g, '');
        }

        $('#search-result').show();
    });
});

// 검색창에서 엔터키를 눌렀을 때 이벤트 처리
$('#searchBox').on('keyup', function(event) {
    console.log('key1');
    if (event.keyCode == 13) {
        console.log('enter key press');

        $('#searchBox').click();
    }
});

$('#wish-button').click(function() {
    console.log('wish btn click');

    $.ajax({
        type: 'post',
        url: '/api/resultadd',
        contentType: 'application/json',
        data: JSON.stringify(searchResult.search_result),
        success: function(response, status, xhr) {
            console.log('검색리스트 결과 추가 완료', response)

            resultList.result_list = response;
        },
        error: function(request, status, error) {
            alert('검색리스트 결과 추가에 실패하였습니다.')
        }
    });
});
