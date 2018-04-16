package jpastart.main;

import jpastart.jpa.EMF;
import jpastart.reserve.application.*;
import jpastart.reserve.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserMain {
    
    private static JoinService joinService = new JoinService();
    private static GetUserService getUserService = new GetUserService();
    private static ChangeNameService changeNameService = new ChangeNameService();
    private static GetUserListService listService = new GetUserListService();
    private static WithdrawService withdrawService = new WithdrawService();
    
    public static void main(String[] args) throws IOException {
        EMF.init(); // 어플리케이션 실행될때 EntityManagerFactory 초기화.
    
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
        while (true) {
            System.out.println("명령어를 입력하세요.");
            String line = reader.readLine();
            String[] commands = line.split(" ");
            if("exit".equalsIgnoreCase(commands[0])) {
                System.out.println("종료합니다!!!");
                break;
            } else if ("join".equalsIgnoreCase(commands[0])) {
                handleJoinCommand(commands);
            } else if ("view".equalsIgnoreCase(commands[0])) {
                handleViewCommnad(commands);
            } else if ("list".equalsIgnoreCase(commands[0])) {
                handleListCommand();
            } else if ("changename".equalsIgnoreCase(commands[0])) {
                handleChangeName(commands);
            } else if ("withdraw".equalsIgnoreCase(commands[0])) {
                handlWithdrawCommand(commands);
            } else {
                System.out.println("올바른 명령어를 입력하세요.");
            }
            System.out.println("-----");
        }
        
        EMF.close(); // 어플리케이션 종료될 때 EntityManagerFactory 종료.
    }
    
    private static void handleJoinCommand(String[] cmd) {
        if(cmd.length != 3) {
            System.out.println("명령어가 올바르지 않습니다.");
            System.out.println("사용법 : join 이메일 이름");
            return;
        }
    
        try {
            joinService.join(new User(cmd[1], cmd[2], new Date()));
            System.out.println("가입 요청을 처리했습니다.");
        }
        catch (DuplicateEmailException e) {
            System.out.println("동일 이메일이 이미 존재합니다.");
        }
    }
    
    private static void handleViewCommnad(String[] cmd) {
        if(cmd.length != 2) {
            System.out.println("명령어가 올바르지 않습니다.");
            System.out.println("사용법 : view 이메일");
            return;
        }
    
        Optional<User> userOpt = getUserService.getUser(cmd[1]);
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("이름 : " + user.getName());
            System.out.printf("생성 : %tY-%<tm-%<td\n", user.getCreateDate());
        }
        else {
            System.out.println("존재하지 않습니다.");
        }
    }
    
    private static void handleChangeName(String[] cmd) {
        if(cmd.length != 3) {
            System.out.println("명령어가 올바르지 않습니다.");
            System.out.println("사용법 : changename 이메일 새이름");
            return;
        }
        try {
            changeNameService.changeName(cmd[1], cmd[2]);
            System.out.println("이름을 변경했습니다.");
        }
        catch (UserNotFoundException ex) {
            System.out.println("존재하지 않습니다.");
        }
    }
    
    private static void handleListCommand() {
        List<User> users = listService.getAllUsers();
        if(users.isEmpty()) {
            System.out.println("사용자가 없습니다.");
        }
        else {
            users.forEach(user -> {
                System.out.printf("| %s | %s | %tY-%<tm-%<td |\n",user.getEmail(), user.getName(), user.getCreateDate());
            });
        }
    }
    
    private static void handlWithdrawCommand(String[] cmd) {
        if(cmd.length != 2) {
            System.out.println("명령어가 올바르지 않습니다.");
            System.out.println("사용법 : withdraw 이메일");
            return;
        }
    
        try {
            withdrawService.withdraw(cmd[1]);
            System.out.println("탈퇴 처리!!!");
        }
        catch (UserNotFoundException e) {
            System.out.println("유저 없다!!");
        }
    }
}
