package com.cos.blog.test;

import com.cos.blog.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {

    @Autowired  // 의존성 주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }

        return "삭제되었습니다.id:"+id;
    }

    //json방식
    //json데이터를 요청=> Java Object
    //MessageConverter의 Jackson라이브러리가 변환해서 받아준다.
    @Transactional //함수 종료시에 자동 commit이 됨.
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        System.out.println("id:"+id);
        System.out.println(("password:"+requestUser.getPassword()));
        System.out.println("email:" + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        //userRepository.save(user);
        
        //더티 체킹
        return null;
    }

    //http://localhost:8000/blog/dummy/users
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    //http://localhost:8000/blog/dummy/user?page=0
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }

    //{id} 주소로 파라미터를 전달받을 수 있음
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")

    public User detail(@PathVariable int id) {
        //람다식으로도 표현 가능
        //.orElseThrow(()->{}); return user;
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id:" + id);
            }
        });
        //요청:웹브라우저
        //but user객체=자바오브젝트
        //@RestController이므로 변환필요(웹브라우저가이해할수있는 데이터로->json)
        //스프링부트=MessageConverter라는 애가 응답시 자동 작동
        //자바 오브젝트가 리턴되면 얘가 Jackson 라이브러리를 호출
        //user 오브젝트를 json으로 변환해서 브라우저에게 던져줌.
        return user;
    }

    //http://localhost:8000/blog/dummy/join (요청)
    //http의 body에 username, password, email 데이터를 가지고 (요청)
    //폼태그 방식
    @PostMapping("/dummy/join")
    public String join(User user) { //key=value (약속된 규칙)
        System.out.println("id:" + user.getUsername());
        System.out.println("username:" + user.getUsername());
        System.out.println("password:" + user.getPassword());
        System.out.println("email:" + user.getEmail());
        System.out.println("role:" + user.getRole());
        System.out.println("createDate:" + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
