# feedback-flow

Esse repositório hospeda o código do primeiro desafio para o Bootcamp Cielo Dev - FullStack

## AWS

Esses passos partem do princípio que um usuário com as permissões adequadas para criar os tópicos e filas e fazer a inscrição dos mesmos já exista.

Passo a passo dos comandos utilizados para fazer o setup dos serviços necessários na AWS

> Para configurar o CLI utilize o comando `aws configure`

### Configurando filas SQS necessários

Use o seguinte comando para substituindo `<QueueName>` para criar cada uma das filas necessárias

```bash
aws sqs create-queue --queue-name <QueueName>.fifo --attributes FifoQueue=true,ContentBasedDeduplication=true
```

### Configurando tópicos SNS necessários

Use o seguinte comando para substituindo `<TopicName>` para criar cada um dos tópicos necessários

```bash
# Definindo atributo FifoTopic para que seja possível fazer a inscrição com as filas SQS
aws sns create-topic --name <TopicName>.fifo --attributes FifoTopic=true,ContentBasedDeduplication=true
```

### Dando permissão de envio de mensagem dos tópicos SNS para as filas SQS

Crie um json seguindo esse formato para cada conjunto de fila-tópico

```json
{
  "Version": "2012-10-17",
  "Id": "__default_policy_ID",
  "Statement": [
    {
      "Sid": "__owner_statement",
      "Effect": "Allow",
      "Principal": {
        "AWS": "arn:aws:iam::<user-id>:root"
      },
      "Action": "SQS:*",
      "Resource": "arn:aws:sqs:<region-id>:<user-id>:<QueueName>.fifo"
    },
    {
      "Sid": "topic-subscription-arn:aws:sns:<region-id>:<user-id>:<TopicName>.fifo",
      "Effect": "Allow",
      "Principal": {
        "AWS": "*"
      },
      "Action": "SQS:SendMessage",
      "Resource": "arn:aws:sqs:<region-id>:<user-id>:<QueueName>.fifo",
      "Condition": {
        "ArnLike": {
          "aws:SourceArn": "arn:aws:sns:<region-id>:<user-id>:<TopicName>.fifo"
        }
      }
    }
  ]
}
```

Execute o seguinte comando para cada fila para permitir que os tópicos referentes possam enviar mensagens para as respectivas filas

```bash
aws sqs set-queue-attributes --queue-url <YourQueueURL> --attributes Policy='<PolicyDocument>'
```

### Comandos para fazer inscrição de filas SQS nos respectivos tópicos

```bash
aws sns subscribe --topic-arn arn:aws:sns:<region-id>:<user-id>:<TopicName>.fifo --protocol sqs --notification-endpoint arn:aws:sqs:<region-id>:<user-id>:<QueueName>.fifo
```
