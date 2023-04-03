package me.hoonti06.osiv.order.command.domain;


import me.hoonti06.osiv.member.command.domain.MemberId;

public interface OrdererService {
    Orderer createOrderer(MemberId ordererMemberId);
}
