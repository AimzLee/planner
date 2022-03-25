package kr.co.team.planner.service.unscribe;

public interface UnscribeService {
     // 바뀌면 true
    // 실패하면 false
    boolean userUnscribecomplete(String status, String nick);


    void userModDateUpdate(String nick);
}

