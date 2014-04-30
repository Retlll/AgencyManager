create table Contract (
    missionID bigint,
    agentID bigint,
    budget bigint,
    startTime date,
    endTime date,
    primary key (missionID, AgentID),
    foreign key (missionID) references Mission(id),
    foreign key (agentID) references Agent(id)
);