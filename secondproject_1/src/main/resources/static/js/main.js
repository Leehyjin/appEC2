const searchResult = new Vue({

    el: '#search-result',
    data: {
        search_result: {}
    }
});

//검색결과 리스트 UI용 Vue 객체
const resultList = new Vue({
    el: '#wish-list-result',
    data: {result_list: {}},
    methods: { deleteResult(resultId){
        console.log('resultlist delete resultid', resultId);

        $.ajax({
        type: 'post'
        url: '/api/resultdelete' + resultId,
        contentType: 'applicaiton/json',
        success: function(response, status, xhr){
        console.log('검색 결과 삭제 완료', response);

        //다시 검색결과 목록 조회
        $.get('/api/resultall', function(response){
            resultList.result_list = response;
        });
        },
        error: function(request, status, error){
                alert('검색리스트 결과 삭제에 실패하였습니다.')}

        });
        },

       addVisitCount(resultId)





    }

})