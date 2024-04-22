#!/bin/bash
export PORT=8080
export HOST=localhost

echo "Wrong length AMEX number"
curl http://${HOST}:${PORT}/api/v1/psp/authorize -s -H'Content-Type: application/json' -d '{"cardNumber": "3444-5555 6666 7778", "expiryDate": "0727", "CVV": "0800", "amount": "750.20", "currency": "USD", "merchantId": "12345"}'
echo
echo
echo "Invalid AMEX number"
curl http://${HOST}:${PORT}/api/v1/psp/authorize -s -H'Content-Type: application/json' -d '{"cardNumber": "3444-5555-6666-777", "expiryDate": "0727", "CVV": "0800", "amount": "750.20", "currency": "USD", "merchantId": "12345"}'
echo
echo
echo "Authorize AMEX transaction"
curl http://${HOST}:${PORT}/api/v1/psp/authorize -s -H'Content-Type: application/json' -d '{"cardNumber": "3742-4545-5400-126", "expiryDate": "07/27", "CVV": "0800", "amount": "750.20", "currency": "USD", "merchantId": "12345"}'
echo
echo
echo "Deny AMEX transaction"
curl http://${HOST}:${PORT}/api/v1/psp/authorize -s -H'Content-Type: application/json' -d '{"cardNumber": "3782-8224-6310-005", "expiryDate": "07/27", "CVV": "0800", "amount": "750.20", "currency": "USD", "merchantId": "12345"}'
echo
echo
echo "Wrong length VISA number"
curl http://${HOST}:${PORT}/api/v1/psp/authorize -s -H'Content-Type: application/json' -d '{"cardNumber": "4263 9826 4026 9299 9", "expiryDate": "07/27", "CVV": "080", "amount": "750.20", "currency": "USD", "merchantId": "12345"}'
echo
echo
echo "Invalid VISA number"
curl http://${HOST}:${PORT}/api/v1/psp/authorize -s -H'Content-Type: application/json' -d '{"cardNumber": "4263 9826 4026 9999", "expiryDate": "07/27", "CVV": "080", "amount": "750.20", "currency": "USD", "merchantId": "12345"}'
echo
echo
echo "Authorize VISA transaction"
curl http://${HOST}:${PORT}/api/v1/psp/authorize -s -H'Content-Type: application/json' -d '{"cardNumber": "4007 7028 3553 2454", "expiryDate": "07/27", "CVV": "080", "amount": "750.20", "currency": "USD", "merchantId": "12345"}'
echo
echo
echo "Deny VISA transaction"
curl http://${HOST}:${PORT}/api/v1/psp/authorize -s -H'Content-Type: application/json' -d '{"cardNumber": "4263 9826 4026 9299", "expiryDate": "07/27", "CVV": "080", "amount": "750.20", "currency": "USD", "merchantId": "12345"}'
echo
echo
echo "Expired VISA card transaction"
curl http://${HOST}:${PORT}/api/v1/psp/authorize -s -H'Content-Type: application/json' -d '{"cardNumber": "4007 7028 3553 2454", "expiryDate": "07/07", "CVV": "080", "amount": "750.20", "currency": "USD", "merchantId": "12345"}'
echo
echo
curl http://${HOST}:${PORT}/api/v1/psp/list
echo
