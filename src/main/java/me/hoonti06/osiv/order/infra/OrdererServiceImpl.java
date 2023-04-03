package me.hoonti06.osiv.order.infra;

import me.hoonti06.osiv.member.command.domain.MemberId;
import me.hoonti06.osiv.member.query.MemberData;
import me.hoonti06.osiv.member.query.MemberQueryService;
import me.hoonti06.osiv.order.command.domain.Orderer;
import me.hoonti06.osiv.order.command.domain.OrdererService;
import org.springframework.stereotype.Service;

@Service
public class OrdererServiceImpl implements OrdererService {
    private MemberQueryService memberQueryService;

    public OrdererServiceImpl(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @Override
    public Orderer createOrderer(MemberId ordererMemberId) {
        MemberData memberData = memberQueryService.getMemberData(ordererMemberId.getId());
        return new Orderer(MemberId.of(memberData.getId()), memberData.getName());
    }
}
