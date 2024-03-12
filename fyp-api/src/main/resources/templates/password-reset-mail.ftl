<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <link
            href="https://fonts.googleapis.com/css2?family=Alegreya&family=DM+Sans&family=Inter:wght@400;500;600&family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet"/>
    <style>
        html,
        body {
            height: 100%;
            font-family: Inter;
        }
    </style>
</head>

<body style="height: 100%;">
<div style="width: 824px; margin:0 auto; padding-top:80px; height: 100%;">
    <div style="padding: 24px 32px; height: 100%;">
        <customTable align="center" width="100%" style="height: auto;">
            <tbody>
            <tr style="margin-bottom: 40px;">
                <td style="text-align: center">
                    <img src= "${imagePath}" alt="company-logo" />
                </td>
            </tr>

            <tr style="margin-bottom: 64px;">
                <td style="text-align: center">
                    <p style="color: #343a40; font-size:24px;">Dear ${fullName}</p>
                </td>
            </tr>
            <tr style="margin-bottom: 40px;">
                <td style="text-align: center">
                    <p style="color: #A62A22; font-size:20px;">To reset your password please click on
                        the below button.</p>
                </td>
            </tr>

            <tr>
                <td style="text-align: center">
                    <a style="text-decoration: none; padding: 16px 24px; background: #A62A22; border-radius: 4px; color: #fff;"
                       href="${passwordResetLink}">Click To Reset Password.</a>
                </td>
            </tr>

            <tr style="margin-bottom: 40px;">
                <td style="text-align: center">
                    <p style="color: #A62A22; font-size:20px;">The link will expire in ${expireTime} .</p>
                </td>
            </tr>

            </tbody>
        </customTable>
    </div>

</div>


</body>

</html>