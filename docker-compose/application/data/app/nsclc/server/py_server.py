from py.thrift.generated import PredictService
from PredictServiceImpl import PredictServiceImpl
from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import  TCompactProtocol
from thrift.server import TServer


try:
    personServiceHandler = PredictServiceImpl()
    processor = PredictService.Processor(personServiceHandler)

    serverSocket = TSocket.TServerSocket(host='127.0.0.1',port=8457)
    transportFactory = TTransport.TFramedTransportFactory()
    protocolFactory = TCompactProtocol.TCompactProtocolFactory()
    server = TServer.TThreadPoolServer(processor,serverSocket,transportFactory,protocolFactory)
    server.serve()

except Thrift.TException as ex:
    print(ex.message)