package org.zerock.b01.service;

import org.zerock.dto.MemberJoinDTO;

public interface MemberService {
    static class MidExistException extends Exception {

    }
    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
}
