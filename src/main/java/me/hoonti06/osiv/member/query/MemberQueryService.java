package me.hoonti06.osiv.member.query;

import me.hoonti06.osiv.member.command.application.NoMemberException;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryService {
    private MemberDataDao memberDataDao;

    public MemberQueryService(MemberDataDao memberDataDao) {
        this.memberDataDao = memberDataDao;
    }

    public MemberData getMemberData(String memberId) {
        MemberData memberData = memberDataDao.findById(memberId);
        if (memberData == null) {
            throw new NoMemberException();
        }
        return memberData;
    }
}
