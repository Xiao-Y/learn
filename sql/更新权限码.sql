update sys_permission set permission_code = (
    CASE
        system_module
        WHEN 'admin-system' THEN
            CONCAT(
                    'sys:',
                    REPLACE(
                            REPLACE(
                                    CONCAT(LOWER(LEFT (permission_code, 1)), SUBSTRING(permission_code, 2, (LENGTH(permission_code) - 1))),
                                    '-',
                                    ':'
                                ),
                            'Api',
                            ''
                        )
                )
        WHEN 'admin-user' THEN
            CONCAT(
                    'user:',
                    REPLACE(
                            REPLACE(
                                    CONCAT(LOWER(LEFT (permission_code, 1)), SUBSTRING(permission_code, 2, (LENGTH(permission_code) - 1))),
                                    '-',
                                    ':'
                                ),
                            'Api',
                            ''
                        )
                )
        WHEN 'core-product' THEN
            CONCAT(
                    'pms:',
                    REPLACE(
                            REPLACE(
                                    CONCAT(LOWER(LEFT (permission_code, 1)), SUBSTRING(permission_code, 2, (LENGTH(permission_code) - 1))),
                                    '-',
                                    ':'
                                ),
                            'Api',
                            ''
                        )
                )
        WHEN 'public-job' THEN
            CONCAT(
                    'job:',
                    REPLACE(
                            REPLACE(
                                    CONCAT(LOWER(LEFT (permission_code, 1)), SUBSTRING(permission_code, 2, (LENGTH(permission_code) - 1))),
                                    '-',
                                    ':'
                                ),
                            'Api',
                            ''
                        )
                )
        WHEN 'core-order' THEN
            CONCAT(
                    'oms:',
                    REPLACE(
                            REPLACE(
                                    CONCAT(LOWER(LEFT (permission_code, 1)), SUBSTRING(permission_code, 2, (LENGTH(permission_code) - 1))),
                                    '-',
                                    ':'
                                ),
                            'Api',
                            ''
                        )
                )
        WHEN 'core-search' THEN
            CONCAT(
                    'sch:',
                    REPLACE(
                            REPLACE(
                                    CONCAT(LOWER(LEFT (permission_code, 1)), SUBSTRING(permission_code, 2, (LENGTH(permission_code) - 1))),
                                    '-',
                                    ':'
                                ),
                            'Api',
                            ''
                        )
                )
        WHEN 'core-seckill' THEN
            CONCAT(
                    'sk:',
                    REPLACE(
                            REPLACE(
                                    CONCAT(LOWER(LEFT (permission_code, 1)), SUBSTRING(permission_code, 2, (LENGTH(permission_code) - 1))),
                                    '-',
                                    ':'
                                ),
                            'Api',
                            ''
                        )
                )
        WHEN 'public-auth' THEN
            CONCAT(
                    'auth:',
                    REPLACE(
                            REPLACE(
                                    CONCAT(LOWER(LEFT (permission_code, 1)), SUBSTRING(permission_code, 2, (LENGTH(permission_code) - 1))),
                                    '-',
                                    ':'
                                ),
                            'Api',
                            ''
                        )
                )
        ELSE ''
        END
    )