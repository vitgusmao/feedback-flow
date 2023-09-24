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
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "sns.amazonaws.com"
      },
      "Action": "sqs:SendMessage",
      "Resource": "arn:aws:sqs:<region-id>:<user-id>:<QueueName>.fifo",
      "Condition": {
        "ArnEquals": {
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


## Configurando variáveis de ambiente

Na pasta raiz do repositorio terá um arquivo chamado `sample.env` copie para `.env` no mesmo diretorio
> Agora dentro do arquivo `.env` terá as seguintes variáveis: 

```env
AWS_ACCESS_KEY=AWS_ACCESS_KEY_SAMPLE
AWS_SECRET_KEY=AWS_SECRET_KEY_SAMPLE

AWS_SNS_TOPIC_SUGGESTION=arn:aws:sqs:<region-id>:<user-id>:<TopicName>.fifo
AWS_SNS_TOPIC_COMPLIMENT=arn:aws:sqs:<region-id>:<user-id>:<TopicName>.fifo
AWS_SNS_TOPIC_CRITIQUE=arn:aws:sqs:<region-id>:<user-id>:<TopicName>.fifo

AWS_SQS_QUEUE_SUGGESTION=https://sqs.<region-id>.amazonaws.com/<user-id>/<TopicName>.fifo
AWS_SQS_QUEUE_COMPLIMENT=https://sqs.<region-id>.amazonaws.com/<user-id>/<TopicName>.fifo
AWS_SQS_QUEUE_CRITIQUE=https://sqs.<region-id>.amazonaws.com/<user-id>/<TopicName>.fifo

TIME_PROCESSING_QUEUE=10000
```
> Substitua as credencias de acesso da amazon pelas suas, que podem ser obtidas utilizando o serviço IAM no console da AWS
> Substitua as informações SNS e SQS com os dados obtidos pelos comandos anteriores