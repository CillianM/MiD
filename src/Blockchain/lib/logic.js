/**
 * Update the status of a certificate
 * @param {ie.mid.UpdateStatus} updateStatus - the certificate to be updated
 * @transaction
 */
function updateStatus(update) {
    update.certificate.status = update.newStatus
    return getAssetRegistry('ie.mid.Certificate')
        .then(function (assetRegistry) {
            return assetRegistry.update(update.certificate);
        });
}
