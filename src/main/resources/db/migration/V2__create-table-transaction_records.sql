create table transaction(

    id bigint                               not null          auto_increment,
    type        varchar(20)      not null,
    amount                  DECIMAL         not null,
    total                   DECIMAL         not null,

    user_id bigint                         not null,
    ref_user_id bigint                         not null,

    primary key(id),

    constraint fk_transaction_user_id foreign key(user_id) references users(id),
    constraint fk_transaction_ref_user_id foreign key(ref_user_id) references users(id)

);