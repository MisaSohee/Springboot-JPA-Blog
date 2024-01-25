package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

@RestController
//사용자가 요청할 때 Date를 응답 해줌()
public class HttpControllerTest {


    private static final String TAG = "HttpControllerTest:";

    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member m = new Member(1,"jinseop","1234","email");
        System.out.println(TAG+"getter:"+ m.getId());
        m.setId(5000);
        System.out.println(TAG+"setter:"+m.getId());
        return "lombok test 완료";
    }


    //인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "get 요청:"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
    }

    //인터넷 브라우저 주소로 이동해도 에러만 뜸.
    //http://localhost:8080/http/post (insert)
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m) {
        return "post 요청:"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
    }

    //http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest() {
        return "put 요청";
    }

    //http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
